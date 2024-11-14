# Shape Manipulation Android App

A simple yet powerful Android application for creating, manipulating, and persisting geometric shapes. The app demonstrates touch interactions, gesture detection, and state management in Android.

## Setup Instructions

1. Clone the repository
2. Open the project in Android Studio
3. Ensure you have the following requirements:
   - Android Studio Arctic Fox or newer
   - Minimum SDK: Android API 21 (Android 5.0)
   - Kotlin version: 1.8.0 or higher

4. Build and run the project on an emulator or physical device

## Architecture Overview

The application follows a modular architecture with clear separation of concerns:

### Core Components

- `MainActivity`: Entry point of the application, manages the main layout and shape creation dialog
- `ShapeView`: Custom view handling shape rendering and touch interactions
- `Shape`: Data class representing individual shapes with properties like position, rotation, and scale
- `ShapeStateManager`: Handles persistence of shapes using SharedPreferences
- `RotationGestureDetector`: Custom gesture detector for rotation interactions
- `ShapeCreationDialog`: Dialog for creating new shapes with customizable properties

### Data Flow

```
User Input → ShapeView → Shape Objects → ShapeStateManager → SharedPreferences
```

## Implemented Features

### Shape Management
- Create circles and squares with customizable colors
- Initial shape placement at (300, 300) coordinates
- Double-tap to delete shapes
- Persistent storage of shapes across app sessions

### Touch Interactions
- Drag shapes with single-finger touch
- Two-finger pinch to scale shapes (0.5x to 2.0x range)
- Two-finger rotation gesture
- Shape selection with touch

### Visual Feedback
- Real-time display of shape properties:
  - Position (X, Y coordinates)
  - Rotation angle
  - Scale factor
- Visual highlighting of selected color during shape creation

### Color Options
- Blue (default)
- Red
- Green
- Yellow
- Magenta

## Performance Considerations

1. **Efficient Drawing**
   - Uses Hardware acceleration with `ANTI_ALIAS_FLAG`
   - Canvas transformations (translate, rotate, scale) for optimal rendering
   - Efficient shape hit detection using mathematical calculations

2. **Memory Management**
   - Reusable Paint objects
   - Efficient shape storage using basic data structures
   - Minimal object creation during touch events

3. **State Management**
   - Asynchronous shape persistence using `SharedPreferences.apply()`
   - Optimized JSON serialization for shape storage
   - Efficient state restoration on app launch

## Known Limitations

1. **Storage**
   - Uses SharedPreferences, which is suitable for small datasets but may not be optimal for large numbers of shapes
   - JSON serialization might impact performance with very large shape collections

2. **Gesture Detection**
   - Multiple shape selection not supported
   - No support for complex gestures like multi-shape manipulation
   - Rotation and scale gestures cannot be performed simultaneously

3. **Shape Types**
   - Limited to circles and squares
   - No support for custom shapes or polygons
   - Fixed shape size before scaling (100x100 for squares, 50 radius for circles)

4. **Visual Features**
   - No shape borders or stroke width customization
   - No gradient or pattern fill options
   - No shape layering controls (z-index)

## Future Improvements

1. **Features**
   - Add support for more shape types
   - Implement shape layering
   - Add undo/redo functionality
   - Support for shape grouping

2. **Performance**
   - Implement Room database for better persistence
   - Add vector drawable support
   - Optimize rendering for large numbers of shapes

3. **UI/UX**
   - Add shape editing mode
   - Implement grid snapping
   - Add shape alignment guides
   - Support for custom color picker

## Contributing

Feel free to submit issues and enhancement requests!