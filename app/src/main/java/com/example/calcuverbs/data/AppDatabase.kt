package com.example.calcuverbs.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase



@Entity
data class Pronoun(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val pronoun: String
)

@Entity(tableName = "verbs")
data class Verb(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val baseForm: String,
    val pastSimple: String,
    val pastParticiple: String,
    val presentPerfect: String,
    val pastPerfect: String,
    val isRegular: Boolean,
    val modulo: String // Nuevo campo para diferenciar módulos
)



@Entity(tableName = "tenses")
data class Tense(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String // Ejemplo: "Simple Present", "Simple Past", etc.
)




@Entity(tableName = "auxiliaries")
data class Auxiliary(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val baseForm: String,       // Forma base (Do / Have)
    val pastSimple: String,     // Pasado simple (Did / Had)
    val presentPerfect: String, // Presente perfecto (Have / Has)
    val pastPerfect: String     // Pasado perfecto (Had)
)



@Entity
data class Rule(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: String,
    val structure: String
)

@Entity(tableName = "modals")
data class Modal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val modal: String
)

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val screenId: String, // Identifica la pantalla
    val content: String
)


@Dao
interface NoteDao {
    @Query("SELECT * FROM notes WHERE screenId = :screenId LIMIT 1")
    suspend fun getNoteForScreen(screenId: String): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Query("DELETE FROM notes WHERE screenId = :screenId")
    suspend fun clearNotesForScreen(screenId: String)
}


@Dao
interface VerbDao {
    @Query("SELECT * FROM verbs")
    suspend fun getAllVerbs(): List<Verb>

    @Query("SELECT * FROM verbs WHERE isRegular = :isRegular")
    suspend fun getVerbsByRegularity(isRegular: Boolean): List<Verb>

    @Query("SELECT * FROM verbs WHERE modulo = :modulo")
    suspend fun getVerbsByModulo(modulo: String): List<Verb>

    @Query("SELECT * FROM verbs WHERE isRegular = :isRegular AND modulo = :modulo")
    suspend fun getVerbsByRegularityAndModulo(isRegular: Boolean, modulo: String): List<Verb>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVerbs(verbs: List<Verb>)

    @Query("""
    SELECT 
        CASE 
            WHEN :tense = 'Simple Present' THEN baseForm
            WHEN :tense = 'Simple Past' THEN pastSimple
            WHEN :tense = 'Past Participle' THEN pastParticiple
            WHEN :tense = 'Present Perfect' THEN presentPerfect
            WHEN :tense = 'Past Perfect' THEN pastPerfect
            ELSE baseForm
        END AS verbForm
    FROM verbs 
    WHERE modulo = :modulo
""")
    suspend fun getVerbsByModuloAndTense(modulo: String, tense: String): List<String>



}




@Dao
interface ModalDao {
    @Query("SELECT * FROM modals")
    suspend fun getAllModals(): List<Modal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModals(modals: List<Modal>)
}

@Dao
interface PronounDao {
    @Query("SELECT * FROM pronoun")
    suspend fun getAllPronouns(): List<Pronoun>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPronouns(pronouns: List<Pronoun>)
}

@Dao
interface RuleDao {
    @Query("SELECT * FROM rule")
    suspend fun getAllRules(): List<Rule>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRules(rules: List<Rule>)

}

@Dao
interface TenseDao {
    @Query("SELECT * FROM tenses")
    suspend fun getAllTenses(): List<Tense>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTenses(tenses: List<Tense>)
}

@Dao
interface AuxiliaryDao {
    @Query("SELECT * FROM auxiliaries")
    suspend fun getAllAuxiliaries(): List<Auxiliary>

    @Query("SELECT * FROM auxiliaries WHERE baseForm = :baseForm LIMIT 1")
    suspend fun getAuxiliaryByBaseForm(baseForm: String): Auxiliary?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuxiliaries(auxiliaries: List<Auxiliary>)
}




@Database(entities = [Pronoun::class, Verb::class, Rule::class, Modal::class, Note::class, Tense::class, Auxiliary::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun verbDao(): VerbDao
    abstract fun modalDao(): ModalDao
    abstract fun pronounDao(): PronounDao
    abstract fun ruleDao(): RuleDao
    abstract fun noteDao(): NoteDao
    abstract fun tenseDao(): TenseDao
    abstract fun auxiliaryDao(): AuxiliaryDao




    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "verbs_database"
                )
                    .fallbackToDestructiveMigration() // Para actualizar la BD sin perder datos en desarrollo
                    .addCallback(DatabaseCallback())
                    .build().also { INSTANCE = it }
            }
        }
    }

    private class DatabaseCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                DatabaseInitializer.initialize(database)
            }
        }
    }
}

