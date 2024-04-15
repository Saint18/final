public interface InterfaceGui {

    /**
     * Initializes and displays the GUI components.
     */
    void initialize();

    /**
     * Updates the GUI components based on the latest data or user interactions.
     */
    void update();

    /**
     * Shows a message or alert to the user.
     * @param message The message to display.
     */
    void showMessage(String message);

    /**
     * Clears or resets the GUI components to their default state.
     */
    void clear();
}
