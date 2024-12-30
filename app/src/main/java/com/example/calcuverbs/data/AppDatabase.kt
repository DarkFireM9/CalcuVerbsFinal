package com.example.calcuverbs.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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
    val isRegular: Boolean
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVerbs(verbs: List<Verb>)
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

@Database(entities = [Pronoun::class, Verb::class, Rule::class, Modal::class, Note::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun verbDao(): VerbDao
    abstract fun modalDao(): ModalDao
    abstract fun pronounDao(): PronounDao
    abstract fun ruleDao(): RuleDao
    abstract fun noteDao(): NoteDao

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
