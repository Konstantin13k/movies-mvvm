package od.konstantin.myapplication.domain

import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.Actor
import od.konstantin.myapplication.data.models.Movie

class MoviesDataSource {

    val movies: List<Movie>

    init {
        movies = arrayListOf(
            Movie(
                posterId = R.drawable.avengers_poster,
                smallPosterId = R.drawable.avenger_small_poster,
                movieTitle = "Avengers: End Game",
                tags = arrayListOf("Action, Adventure, Fantasy"),
                pg = 13,
                rating = 4.0f,
                reviews = 125,
                storyline = "After the devastating events of Avengers: Infinity War, the universe is in ruins due to the efforts of the Mad Titan, Thanos. With the help of remaining allies, the Avengers must assemble once more in order to undo Thanos' actions and restore order to the universe once and for all, no matter what consequences may be in store.",
                movieLength = 137,
                arrayListOf(
                    Actor(R.drawable.robert, fullName = "Robert Downey Jr."),
                    Actor(R.drawable.chris_hemsworth, fullName = "Chris Hemsworth"),
                    Actor(R.drawable.mark, fullName = "Mark Ruffalo"),
                    Actor(R.drawable.chris_evans, fullName = "Chris Evans"),
                    Actor(R.drawable.chris_evans, fullName = "Chris Evans Test 1"),
                    Actor(R.drawable.chris_evans, fullName = "Chris Evans Test 2"),
                    Actor(R.drawable.chris_evans, fullName = "Chris Evans Test 3"),
                )
            ),
            Movie(
                posterId = R.drawable.avengers_poster,
                smallPosterId = R.drawable.tenet,
                movieTitle = "Tenet",
                tags = arrayListOf("Action, Sci-Fi, Thriller"),
                pg = 16,
                rating = 5.0f,
                reviews = 98,
                storyline = "Armed with only one word - Tenet - and fighting for the survival of the entire world, the Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
                movieLength = 97,
            ),
            Movie(
                posterId = R.drawable.avengers_poster,
                smallPosterId = R.drawable.black_widow,
                movieTitle = "Black Widow",
                tags = arrayListOf("Action, Adventure, Sci-Fi"),
                pg = 13,
                rating = 4.0f,
                reviews = 38,
                storyline = "Natasha Romanoff, also known as Black Widow, confronts the darker parts of her ledger when a dangerous conspiracy with ties to her past arises. Pursued by a force that will stop at nothing to bring her down, Natasha must deal with her history as a spy and the broken relationships left in her wake long before she became an Avenger.",
                movieLength = 102,
            ),
            Movie(
                posterId = R.drawable.avengers_poster,
                smallPosterId = R.drawable.wonder_woman_small_poster,
                movieTitle = "Wonder Woman 1984",
                tags = arrayListOf("Action, Adventure, Fantasy"),
                pg = 13,
                rating = 5.0f,
                reviews = 74,
                storyline = "Wonder Woman comes into conflict with the Soviet Union during the Cold War in the 1980s and finds a formidable foe by the name of the Cheetah.",
                movieLength = 120,
            ),
            Movie(
                posterId = R.drawable.avengers_poster,
                smallPosterId = R.drawable.iron_man,
                movieTitle = "Iron Man",
                tags = arrayListOf("Action, Adventure, Fantasy"),
                pg = 13,
                rating = 4.0f,
                reviews = 70,
                storyline = "After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.",
                movieLength = 126,
            ),
            Movie(
                posterId = R.drawable.avengers_poster,
                smallPosterId = R.drawable.knives_out,
                movieTitle = "Knives Out",
                tags = arrayListOf("Mystery, Comedy, Drama, Crime"),
                pg = 13,
                rating = 4.0f,
                reviews = 9,
                storyline = "When renowned crime novelist Harlan Thrombey is found dead at his estate just after his 85th birthday, the inquisitive and debonair Detective Benoit Blanc is mysteriously enlisted to investigate. From Harlan's dysfunctional family to his devoted staff, Blanc sifts through a web of red herrings and self-serving lies to uncover the truth behind Harlan's untimely death.",
                movieLength = 131,
            ),
        )
    }
}