package com.example.calcuverbs.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseInitializer {

    fun initialize(database: AppDatabase) {
        val verbDao = database.verbDao()
        val modalDao = database.modalDao()
        val pronounDao = database.pronounDao()
        val ruleDao = database.ruleDao()

        CoroutineScope(Dispatchers.IO).launch {
            // Datos iniciales
            val initialVerbs = listOf(
                Verb(baseForm = "Answer", pastSimple = "Answered", pastParticiple = "Answered", isRegular = true),
                Verb(baseForm = "Add", pastSimple = "Added", pastParticiple = "Added", isRegular = true),
                Verb(baseForm = "Apologise", pastSimple = "Apologised", pastParticiple = "Apologised", isRegular = true),
                Verb(baseForm = "Arrest", pastSimple = "Arrested", pastParticiple = "Arrested", isRegular = true),
                Verb(baseForm = "Arrive", pastSimple = "Arrived", pastParticiple = "Arrived", isRegular = true),
                Verb(baseForm = "Ask", pastSimple = "Asked", pastParticiple = "Asked", isRegular = true),
                Verb(baseForm = "Attack", pastSimple = "Attacked", pastParticiple = "Attacked", isRegular = true),
                Verb(baseForm = "Be", pastSimple = "Was/Were", pastParticiple = "Been", isRegular = false),
                Verb(baseForm = "Become", pastSimple = "Became", pastParticiple = "Become", isRegular = false),
                Verb(baseForm = "Begin", pastSimple = "Began", pastParticiple = "Begun", isRegular = false),
                Verb(baseForm = "Believe", pastSimple = "Believed", pastParticiple = "Believed", isRegular = true),
                Verb(baseForm = "Boil", pastSimple = "Boiled", pastParticiple = "Boiled", isRegular = true),
                Verb(baseForm = "Book", pastSimple = "Booked", pastParticiple = "Booked", isRegular = true),
                Verb(baseForm = "Borrow", pastSimple = "Borrowed", pastParticiple = "Borrowed", isRegular = true),
                Verb(baseForm = "Break", pastSimple = "Broke", pastParticiple = "Broken", isRegular = false),
                Verb(baseForm = "Bring", pastSimple = "Brought", pastParticiple = "Brought", isRegular = false),
                Verb(baseForm = "Build", pastSimple = "Built", pastParticiple = "Built", isRegular = false),
                Verb(baseForm = "Buy", pastSimple = "Bought", pastParticiple = "Bought", isRegular = false),
                Verb(baseForm = "Carry", pastSimple = "Carried", pastParticiple = "Carried", isRegular = true),
                Verb(baseForm = "Catch", pastSimple = "Caught", pastParticiple = "Caught", isRegular = false),
                Verb(baseForm = "Change", pastSimple = "Changed", pastParticiple = "Changed", isRegular = true),
                Verb(baseForm = "Chop", pastSimple = "Chopped", pastParticiple = "Chopped", isRegular = true),
                Verb(baseForm = "Clean", pastSimple = "Cleaned", pastParticiple = "Cleaned", isRegular = true),
                Verb(baseForm = "Climb", pastSimple = "Climbed", pastParticiple = "Climbed", isRegular = true),
                Verb(baseForm = "Collect", pastSimple = "Collected", pastParticiple = "Collected", isRegular = true),
                Verb(baseForm = "Come", pastSimple = "Came", pastParticiple = "Come", isRegular = false),
                Verb(baseForm = "Compose", pastSimple = "Composed", pastParticiple = "Composed", isRegular = true),
                Verb(baseForm = "Cook", pastSimple = "Cooked", pastParticiple = "Cooked", isRegular = true),
                Verb(baseForm = "Copy", pastSimple = "Copied", pastParticiple = "Copied", isRegular = true),
                Verb(baseForm = "Cut", pastSimple = "Cut", pastParticiple = "Cut", isRegular = false),
                Verb(baseForm = "Dance", pastSimple = "Danced", pastParticiple = "Danced", isRegular = true),
                Verb(baseForm = "Describe", pastSimple = "Described", pastParticiple = "Described", isRegular = true),
                Verb(baseForm = "Destroy", pastSimple = "Destroyed", pastParticiple = "Destroyed", isRegular = true),
                Verb(baseForm = "Die", pastSimple = "Died", pastParticiple = "Died", isRegular = true),
                Verb(baseForm = "Discover", pastSimple = "Discovered", pastParticiple = "Discovered", isRegular = true),
                Verb(baseForm = "Discuss", pastSimple = "Discussed", pastParticiple = "Discussed", isRegular = true),
                Verb(baseForm = "Do", pastSimple = "Did", pastParticiple = "Done", isRegular = false),
                Verb(baseForm = "Draw", pastSimple = "Drew", pastParticiple = "Drawn", isRegular = false),
                Verb(baseForm = "Dream", pastSimple = "Dreamt/Dreamed", pastParticiple = "Dreamt/Dreamed", isRegular = true),
                Verb(baseForm = "Drink", pastSimple = "Drank", pastParticiple = "Drunk", isRegular = false),
                Verb(baseForm = "Drive", pastSimple = "Drove", pastParticiple = "Driven", isRegular = false),
                Verb(baseForm = "Dye", pastSimple = "Dyed", pastParticiple = "Dyed", isRegular = true),
                Verb(baseForm = "Eat", pastSimple = "Ate", pastParticiple = "Eaten", isRegular = false),
                Verb(baseForm = "Enjoy", pastSimple = "Enjoyed", pastParticiple = "Enjoyed", isRegular = true),
                Verb(baseForm = "Explode", pastSimple = "Exploded", pastParticiple = "Exploded", isRegular = true),
                Verb(baseForm = "Extinguish", pastSimple = "Extinguished", pastParticiple = "Extinguished", isRegular = true),
                Verb(baseForm = "Fall", pastSimple = "Fell", pastParticiple = "Fallen", isRegular = false),
                Verb(baseForm = "Feed", pastSimple = "Fed", pastParticiple = "Fed", isRegular = false),
                Verb(baseForm = "Feel", pastSimple = "Felt", pastParticiple = "Felt", isRegular = false),
                Verb(baseForm = "Fight", pastSimple = "Fought", pastParticiple = "Fought", isRegular = false),
                Verb(baseForm = "Find", pastSimple = "Found", pastParticiple = "Found", isRegular = false),
                Verb(baseForm = "Fly", pastSimple = "Flew", pastParticiple = "Flown", isRegular = false),
                Verb(baseForm = "Forget", pastSimple = "Forgot", pastParticiple = "Forgotten", isRegular = false),
                Verb(baseForm = "Freeze", pastSimple = "Froze", pastParticiple = "Frozen", isRegular = false),
                Verb(baseForm = "Fry", pastSimple = "Fried", pastParticiple = "Fried", isRegular = true),
                Verb(baseForm = "Give", pastSimple = "Gave", pastParticiple = "Given", isRegular = false),
                Verb(baseForm = "Go", pastSimple = "Went", pastParticiple = "Gone", isRegular = false),
                Verb(baseForm = "Grow", pastSimple = "Grew", pastParticiple = "Grown", isRegular = false),
                Verb(baseForm = "Happen", pastSimple = "Happened", pastParticiple = "Happened", isRegular = true),
                Verb(baseForm = "Hate", pastSimple = "Hated", pastParticiple = "Hated", isRegular = true),
                Verb(baseForm = "Have", pastSimple = "Had", pastParticiple = "Had", isRegular = false),
                Verb(baseForm = "Hear", pastSimple = "Heard", pastParticiple = "Heard", isRegular = false),
                Verb(baseForm = "Help", pastSimple = "Helped", pastParticiple = "Helped", isRegular = true),
                Verb(baseForm = "Hire", pastSimple = "Hired", pastParticiple = "Hired", isRegular = true),
                Verb(baseForm = "Hope", pastSimple = "Hoped", pastParticiple = "Hoped", isRegular = true),
                Verb(baseForm = "Hunt", pastSimple = "Hunted", pastParticiple = "Hunted", isRegular = true),
                Verb(baseForm = "Hurt", pastSimple = "Hurt", pastParticiple = "Hurt", isRegular = false),
                Verb(baseForm = "Imagine", pastSimple = "Imagined", pastParticiple = "Imagined", isRegular = true),
                Verb(baseForm = "Invent", pastSimple = "Invented", pastParticiple = "Invented", isRegular = true),
                Verb(baseForm = "Invite", pastSimple = "Invited", pastParticiple = "Invited", isRegular = true),
                Verb(baseForm = "Jump", pastSimple = "Jumped", pastParticiple = "Jumped", isRegular = true),
                Verb(baseForm = "Keep", pastSimple = "Kept", pastParticiple = "Kept", isRegular = false),
                Verb(baseForm = "Kill", pastSimple = "Killed", pastParticiple = "Killed", isRegular = true),
                Verb(baseForm = "Know", pastSimple = "Knew", pastParticiple = "Known", isRegular = false),
                Verb(baseForm = "Leave", pastSimple = "Left", pastParticiple = "Left", isRegular = false),
                Verb(baseForm = "Lend", pastSimple = "Lent", pastParticiple = "Lent", isRegular = false),
                Verb(baseForm = "Lay", pastSimple = "Laid", pastParticiple = "Laid", isRegular = false),
                Verb(baseForm = "Lie", pastSimple = "Lay", pastParticiple = "Lain", isRegular = false),
                Verb(baseForm = "Lift", pastSimple = "Lifted", pastParticiple = "Lifted", isRegular = true),
                Verb(baseForm = "Like", pastSimple = "Liked", pastParticiple = "Liked", isRegular = true),
                Verb(baseForm = "Listen", pastSimple = "Listened", pastParticiple = "Listened", isRegular = true),
                Verb(baseForm = "Live", pastSimple = "Lived", pastParticiple = "Lived", isRegular = true),
                Verb(baseForm = "Look", pastSimple = "Looked", pastParticiple = "Looked", isRegular = true),
                Verb(baseForm = "Lose", pastSimple = "Lost", pastParticiple = "Lost", isRegular = false),
                Verb(baseForm = "Love", pastSimple = "Loved", pastParticiple = "Loved", isRegular = true),
                Verb(baseForm = "Make", pastSimple = "Made", pastParticiple = "Made", isRegular = false),
                Verb(baseForm = "Meet", pastSimple = "Met", pastParticiple = "Met", isRegular = false),
                Verb(baseForm = "Miss", pastSimple = "Missed", pastParticiple = "Missed", isRegular = true),
                Verb(baseForm = "Offer", pastSimple = "Offered", pastParticiple = "Offered", isRegular = true),
                Verb(baseForm = "Open", pastSimple = "Opened", pastParticiple = "Opened", isRegular = true),
                Verb(baseForm = "Pack", pastSimple = "Packed", pastParticiple = "Packed", isRegular = true),
                Verb(baseForm = "Pass", pastSimple = "Passed", pastParticiple = "Passed", isRegular = true),
                Verb(baseForm = "Pay", pastSimple = "Paid", pastParticiple = "Paid", isRegular = false),
                Verb(baseForm = "Peel", pastSimple = "Peeled", pastParticiple = "Peeled", isRegular = true),
                Verb(baseForm = "Phone", pastSimple = "Phoned", pastParticiple = "Phoned", isRegular = true),
                Verb(baseForm = "Plan", pastSimple = "Planned", pastParticiple = "Planned", isRegular = true),
                Verb(baseForm = "Play", pastSimple = "Played", pastParticiple = "Played", isRegular = true),
                Verb(baseForm = "Pour", pastSimple = "Poured", pastParticiple = "Poured", isRegular = true),
                Verb(baseForm = "Prefer", pastSimple = "Preferred", pastParticiple = "Preferred", isRegular = true),
                Verb(baseForm = "Prepare", pastSimple = "Prepared", pastParticiple = "Prepared", isRegular = true),
                Verb(baseForm = "Push", pastSimple = "Pushed", pastParticiple = "Pushed", isRegular = true),
                Verb(baseForm = "Put", pastSimple = "Put", pastParticiple = "Put", isRegular = false),
                Verb(baseForm = "Rain", pastSimple = "Rained", pastParticiple = "Rained", isRegular = true),
                Verb(baseForm = "Read", pastSimple = "Read", pastParticiple = "Read", isRegular = false),
                Verb(baseForm = "Reduce", pastSimple = "Reduced", pastParticiple = "Reduced", isRegular = true),
                Verb(baseForm = "Remember", pastSimple = "Remembered", pastParticiple = "Remembered", isRegular = true),
                Verb(baseForm = "Rent", pastSimple = "Rented", pastParticiple = "Rented", isRegular = true),
                Verb(baseForm = "Rescue", pastSimple = "Rescued", pastParticiple = "Rescued", isRegular = true),
                Verb(baseForm = "Return", pastSimple = "Returned", pastParticiple = "Returned", isRegular = true),
                Verb(baseForm = "Ring", pastSimple = "Rang", pastParticiple = "Rung", isRegular = false),
                Verb(baseForm = "Run", pastSimple = "Ran", pastParticiple = "Run", isRegular = false),
                Verb(baseForm = "Save", pastSimple = "Saved", pastParticiple = "Saved", isRegular = true),
                Verb(baseForm = "Say", pastSimple = "Said", pastParticiple = "Said", isRegular = false),
                Verb(baseForm = "Scream", pastSimple = "Screamed", pastParticiple = "Screamed", isRegular = true),
                Verb(baseForm = "Search", pastSimple = "Searched", pastParticiple = "Searched", isRegular = true),
                Verb(baseForm = "See", pastSimple = "Saw", pastParticiple = "Seen", isRegular = false),
                Verb(baseForm = "Sell", pastSimple = "Sold", pastParticiple = "Sold", isRegular = false),
                Verb(baseForm = "Send", pastSimple = "Sent", pastParticiple = "Sent", isRegular = false),
                Verb(baseForm = "Shine", pastSimple = "Shone", pastParticiple = "Shone", isRegular = false),
                Verb(baseForm = "Shoot", pastSimple = "Shot", pastParticiple = "Shot", isRegular = false),
                Verb(baseForm = "Shut", pastSimple = "Shut", pastParticiple = "Shut", isRegular = false),
                Verb(baseForm = "Sing", pastSimple = "Sang", pastParticiple = "Sung", isRegular = false),
                Verb(baseForm = "Sit", pastSimple = "Sat", pastParticiple = "Sat", isRegular = false),
                Verb(baseForm = "Skate", pastSimple = "Skated", pastParticiple = "Skated", isRegular = true),
                Verb(baseForm = "Ski", pastSimple = "Skied", pastParticiple = "Skied", isRegular = true),
                Verb(baseForm = "Sleep", pastSimple = "Slept", pastParticiple = "Slept", isRegular = false),
                Verb(baseForm = "Smell", pastSimple = "Smelled", pastParticiple = "Smelled", isRegular = true),
                Verb(baseForm = "Snore", pastSimple = "Snored", pastParticiple = "Snored", isRegular = true),
                Verb(baseForm = "Speak", pastSimple = "Spoke", pastParticiple = "Spoken", isRegular = false),
                Verb(baseForm = "Spend", pastSimple = "Spent", pastParticiple = "Spent", isRegular = false),
                Verb(baseForm = "Start", pastSimple = "Started", pastParticiple = "Started", isRegular = true),
                Verb(baseForm = "Stay", pastSimple = "Stayed", pastParticiple = "Stayed", isRegular = true),
                Verb(baseForm = "Steal", pastSimple = "Stole", pastParticiple = "Stolen", isRegular = false),
                Verb(baseForm = "Stop", pastSimple = "Stopped", pastParticiple = "Stopped", isRegular = true),
                Verb(baseForm = "Study", pastSimple = "Studied", pastParticiple = "Studied", isRegular = true),
                Verb(baseForm = "Survive", pastSimple = "Survived", pastParticiple = "Survived", isRegular = true),
                Verb(baseForm = "Swim", pastSimple = "Swam", pastParticiple = "Swum", isRegular = false),
                Verb(baseForm = "Take", pastSimple = "Took", pastParticiple = "Taken", isRegular = false),
                Verb(baseForm = "Talk", pastSimple = "Talked", pastParticiple = "Talked", isRegular = true),
                Verb(baseForm = "Teach", pastSimple = "Taught", pastParticiple = "Taught", isRegular = false),
                Verb(baseForm = "Tell", pastSimple = "Told", pastParticiple = "Told", isRegular = false),
                Verb(baseForm = "Thank", pastSimple = "Thanked", pastParticiple = "Thanked", isRegular = true),
                Verb(baseForm = "Think", pastSimple = "Thought", pastParticiple = "Thought", isRegular = false),
                Verb(baseForm = "Throw", pastSimple = "Threw", pastParticiple = "Thrown", isRegular = false),
                Verb(baseForm = "Touch", pastSimple = "Touched", pastParticiple = "Touched", isRegular = true),
                Verb(baseForm = "Try", pastSimple = "Tried", pastParticiple = "Tried", isRegular = true),
                Verb(baseForm = "Understand", pastSimple = "Understood", pastParticiple = "Understood", isRegular = false),
                Verb(baseForm = "Use", pastSimple = "Used", pastParticiple = "Used", isRegular = true),
                Verb(baseForm = "Visit", pastSimple = "Visited", pastParticiple = "Visited", isRegular = true),
                Verb(baseForm = "Wait", pastSimple = "Waited", pastParticiple = "Waited", isRegular = true),
                Verb(baseForm = "Walk", pastSimple = "Walked", pastParticiple = "Walked", isRegular = true),
                Verb(baseForm = "Want", pastSimple = "Wanted", pastParticiple = "Wanted", isRegular = true),
                Verb(baseForm = "Warn", pastSimple = "Warned", pastParticiple = "Warned", isRegular = true),
                Verb(baseForm = "Wash", pastSimple = "Washed", pastParticiple = "Washed", isRegular = true),
                Verb(baseForm = "Watch", pastSimple = "Watched", pastParticiple = "Watched", isRegular = true),
                Verb(baseForm = "Wear", pastSimple = "Wore", pastParticiple = "Worn", isRegular = false),
                Verb(baseForm = "Win", pastSimple = "Won", pastParticiple = "Won", isRegular = false),
                Verb(baseForm = "Work", pastSimple = "Worked", pastParticiple = "Worked", isRegular = true),
                Verb(baseForm = "Write", pastSimple = "Wrote", pastParticiple = "Written", isRegular = false)

            )





            val initialModals = listOf(
                Modal(modal = "Can"),
                Modal(modal = "Could"),
                Modal(modal = "May"),
                Modal(modal = "Might"),
                Modal(modal = "Must"),
                Modal(modal = "Shall"),
                Modal(modal = "Should"),
                Modal(modal = "Will"),
                Modal(modal = "Would")
            )

            val initialPronouns = listOf(
                Pronoun(pronoun = "I"),
                Pronoun(pronoun = "You"),
                Pronoun(pronoun = "He"),
                Pronoun(pronoun = "She"),
                Pronoun(pronoun = "It"),
                Pronoun(pronoun = "We"),
                Pronoun(pronoun = "They")
            )

            val initialRules = listOf(
                Rule(type = "affirmative", structure = "\$pronoun \$modal \$verb."),
                Rule(type = "negative", structure = "\$pronoun \$modal not \$verb."),
                Rule(type = "question", structure = "\$modal \$pronoun \$verb?"),
                Rule(type = "positiveResponse", structure = "Yes, \$pronoun \$modal \$verb."),
                Rule(type = "negativeResponse", structure = "No, \$pronoun \$modal not \$verb.")
            )

            // Insertar en la base de datos
            verbDao.insertVerbs(initialVerbs)
            modalDao.insertModals(initialModals)
            pronounDao.insertPronouns(initialPronouns)
            ruleDao.insertRules(initialRules)
        }
    }

}
