package lvc.com.movies.util;

import java.util.HashMap;
import java.util.List;

import lvc.com.movies.R;

/**
 * Created by leonardo2050 on 30/01/18.
 */

public class GenreUtils {

    private static final int WESTERN = 37;
    private static final int WAR = 10752;
    private static final int THRILLER = 53;
    private static final int TV_MOVIE = 10770;
    private static final int SCIENCE_FICTION = 878;
    private static final int ROMANCE = 10749;
    private static final int MYSTERY = 9648;
    private static final int MUSIC = 10402;
    private static final int HORROR = 27;
    private static final int HISTORY = 36;
    private static final int ACTION = 28;
    private static final int ADVENTURE = 12;
    private static final int FANTASY = 14;
    private static final int ANIMATION = 16;
    private static final int COMEDY = 35;
    private static final int CRIME = 80;
    private static final int DOCUMENTARY = 99;
    private static final int DRAMA = 18;
    private static final int FAMILY = 10751;



    public static int[] getGenreReString(List<Integer> genreCodes) {
        int[] resStringsGenre = new int[genreCodes.size()];
        int i = 0;
        for (int genreCode : genreCodes) {
            switch (genreCode) {
                case ACTION:
                    resStringsGenre[i] = R.string.action;
                    break;

                case WESTERN:
                    resStringsGenre[i] = R.string.western;
                    break;

                case WAR:
                    resStringsGenre[i] = R.string.war;
                    break;

                case THRILLER:
                    resStringsGenre[i] = R.string.thriller;
                    break;
                    //
                case TV_MOVIE:
                    resStringsGenre[i] = R.string.tv_movie;
                    break;

                case SCIENCE_FICTION:
                    resStringsGenre[i] = R.string.science_fiction;
                    break;

                case ROMANCE:
                    resStringsGenre[i] = R.string.romance;
                    break;

                case MYSTERY:
                    resStringsGenre[i] = R.string.mystery;
                    break;

                case MUSIC:
                    resStringsGenre[i] = R.string.music;
                    break;

                case HORROR:
                    resStringsGenre[i] = R.string.horror;
                    break;

                case HISTORY:
                    resStringsGenre[i] = R.string.history;
                    break;

                case ADVENTURE:
                    resStringsGenre[i] = R.string.adventure;
                    break;

                case FANTASY:
                    resStringsGenre[i] = R.string.fantasy;
                    break;

                case ANIMATION:
                    resStringsGenre[i] = R.string.animation;
                    break;

                case COMEDY:
                    resStringsGenre[i] = R.string.comedy;
                    break;

                case CRIME:
                    resStringsGenre[i] = R.string.crime;
                    break;

                case DOCUMENTARY:
                    resStringsGenre[i] = R.string.documentary;
                    break;

                case DRAMA:
                    resStringsGenre[i] = R.string.drama;
                    break;

                case FAMILY:
                    resStringsGenre[i] = R.string.family;
                    break;

                default:
                    resStringsGenre[i] = R.string.unknown;
                    break;


            }

            i ++;
        }

        return resStringsGenre;
    }

}
