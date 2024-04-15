import java.io.*;
import java.util.List;

public class Controller {
    private InterfaceWorkoutLogger workoutLogger; // Model to log workouts
    private InterfaceGui gui; // View to interact with the user

    public Controller(InterfaceWorkoutLogger workoutLogger, InterfaceGui gui) {
        this.workoutLogger = workoutLogger;
        this.gui = gui;
        initialize();
    }

    private void initialize() {
        gui.initialize();
        gui.showMessage("Welcome to the Fitness Tracker App!");
    }

    public void addExercise(AbstractExercise exercise) {
        try {
            ConcreteWorkout workout = getCurrentWorkout();
            workout.addExercise(exercise);
            workoutLogger.logWorkout(workout);
            gui.update();
            gui.showMessage("Exercise added successfully.");
        } catch (Exception e) {
            gui.showMessage("Failed to add exercise: " + e.getMessage());
        }
    }

    private ConcreteWorkout getCurrentWorkout() {
        List<AbstractWorkout> workouts = workoutLogger.getLoggedWorkouts();
        if (!workouts.isEmpty()) {
            return (ConcreteWorkout) workouts.get(workouts.size() - 1);
        } else {
            return new ConcreteWorkout("Daily Workout");
        }
    }

    public void displayWorkouts() {
        StringBuilder workoutsDisplay = new StringBuilder();
        for (AbstractWorkout workout : workoutLogger.getLoggedWorkouts()) {
            workoutsDisplay.append(workout.toString()).append("\n");
        }
        gui.showMessage(workoutsDisplay.toString());
    }

    public void exportWorkoutData() {
        File file = new File("WorkoutData.txt");
        try (PrintWriter out = new PrintWriter(file)) {
            workoutLogger.getLoggedWorkouts().forEach(workout -> out.println(workout.toString()));
            gui.showMessage("Data exported successfully to " + file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            gui.showMessage("Failed to export data: " + e.getMessage());
        }
    }

    public void displayStatistics() {
        int totalWorkouts = workoutLogger.getNumberOfWorkouts();
        double totalVolume = workoutLogger.getLoggedWorkouts().stream()
            .flatMap(workout -> workout.getExercises().stream())
            .mapToDouble(AbstractExercise::calculateVolume)
            .sum();
        double averageDuration = workoutLogger.getLoggedWorkouts().stream()
            .mapToInt(AbstractWorkout::getDurationMinutes)
            .average()
            .orElse(0);

        String stats = String.format("Total Workouts: %d\nTotal Volume: %.2f kg\nAverage Workout Duration: %.2f minutes",
                                     totalWorkouts, totalVolume, averageDuration);
        gui.showMessage(stats);
    }
    
    public String getWorkoutsDisplayText() {
        StringBuilder sb = new StringBuilder();
        List<AbstractWorkout> workouts = workoutLogger.getLoggedWorkouts();  // Assuming there's a workoutLogger with a method to get all workouts
        for (AbstractWorkout workout : workouts) {
            sb.append(workout.toString()).append("\n");
            for (AbstractExercise exercise : workout.getExercises()) {
                sb.append("   - ").append(exercise).append("\n");  // Display each exercise in the workout
            }
        }
        return sb.toString();
    }

}
