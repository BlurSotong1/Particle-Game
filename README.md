# Sand Falling Simulator

## Overview
This project is a sand falling simulator implemented in Java. It simulates the behavior of sand particles falling and interacting with each other and the environment.

## Features
- Particle Simulation: Simulates the behavior of sand particles falling and settling based on gravity and collision with obstacles.
- Graphical User Interface (GUI): Provides a graphical interface for interacting with and visualizing the simulation.
- Real-time Rendering: Renders the simulation in real-time to provide immediate feedback to the user.
- Interactive Controls: Allows users to interact with the simulation, such as adding or removing particles, adjusting parameters, and observing particle behavior.

## Components
- `SimulatorScreen`: Manages the main simulation window, including rendering pixels, handling user input, and coordinating the simulation logic.
- `SimulatorLogic`: Implements the core logic for simulating particle behavior, including particle movement, collision detection, and interaction with the environment.
- `RenderLogic`: Handles the rendering of modified pixels on the screen, optimizing rendering performance by updating only the pixels that have changed.
- `Coordinates`: Represents a set of coordinates used to mark modified pixels for rendering.
- `Particle Package`: Contains implementations of different particle types, such as `SandParticle`, each with unique properties and Colour.

  
- `MouseHandler`: Handles mouse input, allowing users to draw particles by clicking and dragging the mouse.
- `KeyboardHandler`: Handles keyboard input, providing shortcuts for clearing the screen or performing other actions.
- `Direction`: Enumerates different directions for particle movement or other actions.

## Usage
To run the sand falling simulator:
1. Clone the repository to your local machine.
2. Compile the Java source files.
3. Run the main application class (`MainAPP.java`).
4. Interact with the GUI to observe and control the simulation.
5. Mouse input places sand. 
6. Pressing SpaceBar clears the screen.

## Dependencies
- Java Development Kit (JDK): Requires JDK to compile and run the Java source code.
- Java Swing: Utilizes Java Swing for creating the graphical user interface and rendering the simulation.

## Future Improvements
- Performance Optimization: Investigate and optimize rendering and simulation logic to improve performance and frame rate.
- Additional Particle Types: Add more particle types to enhance the simulation, such as water, lava, or gas particles, each with unique properties and behavior.
- Particle Interactions: Implement interactions between different types of particles, such as collisions, reactions, or fluid dynamics.
- User Interface Enhancements: Improve the user interface with additional features, controls, and visualizations.
- Double Buffering: Implement double buffering to improve rendering smoothness and reduce flickering.

## Contributing
Contributions to the sand falling simulator project are welcome! Feel free to submit bug reports, feature requests, or pull requests to help improve the simulator.

## License
This project is licensed under the [MIT License](LICENSE).