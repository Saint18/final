public interface InterfaceExercise {

    /**
     * Calculates the total effort of the exercise.
     * The implementation can vary based on the type of exercise.
     * For example, it might consider reps, sets, and duration.
     * @return the total effort as a double.
     */
    double calculateVolume();

    /**
     * Provides a detailed description of the exercise.
     * @return a String describing the exercise.
     */
    String getDescription();
}
