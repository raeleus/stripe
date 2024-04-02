### 1.4.7
* Added SystemCursorListener
* Added SplitPaneSystemCursorListener
* Added PopTable#setStyle(WindowStyle style)
* Fixed CollapsibleGroup not matching the largest available size
* PopTable hover/click/tooltip no longer fails to find a style if the provided style is a WindowStyle
* PopTableHoverListener now has a configurable delay
* Prevent endless loop of enter and exit in PopTableHoverListener
* Clicking now hides the PopTable of the PopTableHoverListener
* PopTableHoverListener can attach the PopTable to an Actor that is different than the Listener Actor

### 1.4.6
* Updated to libGDX 1.12.1
* Updated source compatibility to Java 8
* Added CollapsibleGroup
* Added ToggleGroup
* Fixed KeepSizedWithinStage in PopTable
* Fixed issues with PopTable focus
* Other minor PopTable bug fixes
* ScrollFocusListener now sets focus on the correct child
* Fixed issues with holding buttons in Spinner
* Added minor UI improvements and event handling to PopColorPicker
* Added DraggableList improvements to events and selecting Actors
* The ViewportWidget must now be manually updated via ViewportWidget#updateViewport() before drawing. This resolves incorrect rendering issues.
* PopColorPicker no longer depends on TextraTypist as a whole, since it only needed one file from it.

### 1.4.5
* Fixed TextraTypist GWT tag.

### 1.4.4
* Fixed javadoc build. Thanks TEttinger!

### 1.4.3
* Updated Regexodus to 0.1.5.
* Updated Textratypist to 0.8.0.

### 1.4.2 
* Fixed creating ResizeWidget with a null style causes an NPE. Resolves #6
* Updated TextraTypist version in PopColorPicker

### 1.4.1
* Included TextraTypist in ColorPicker project. Eliminated local copy of ColorUtils

### 1.4.0
* Added ColorPicker widget.
* Added option to disable centering when layout is called in ResizeWidget.
* Added option to attach a PopTable to the position of the mouse.
* Added ability to supress key listeners in PopTable.
* Added methods to set/get the stage background for PopTable.
* Added method to get the parent group that PopTable belongs to.
* Added no-argument constructor for PopTableClickListener.

### 1.3.1
* Fixed PopTable from calling layout in an endless loop when automaticallyResized and keepSizedWithinStage are both selected.
* Fixed DraggableSelectBox not setting the stage's scroll focus. 
* Fixed DraggableSelectBox's PopTable not matching the width of the button.

### 1.3.0
* Added PopTable constructor that accepts a WindowStyle.
* Prevent PopTable from crashing when setFillParent(true) is called.
* PopTable no longer calls layout every single frame.

### 1.2.0
* PopTable's position can be offset by a specified pixel value when attached to an actor.

### 1.1.0
* Added GridDrawable.
* Added AspectRatioContainer.
* Added highlightActor to PopTable to draw widgets above the stageBackground drawable for demonstrations.
* Added constructor to PopTableStyle to derive the fields from an existing WindowStyle.
* Added draggable setting to PopTable.

### 1.0.0
* Moved FreeTypeSkin and FreeTypeSkinLoader to separate subproject.

### 0.2.1
* Fixed ViewportWidget not working with all viewports (fill, fit, etc.)

### 0.2.0
* Added ScrollFocusListener
* PopTable can no longer be clicked on while hiding.
* Fixed PopTable aligning to widgets removed from the Stage.
* Fixed PopTableTooltipListener activating when removed from the Stage.
* Fixed PopTableTooltipListener showing the tooltip in the incorrect position.
* Fixed StripeMenu not hiding when a dialog is shown in a ChangeEvent.

### 0.1.2
* StripeMenu no longer closes when user clicks on the title of a submenu.

### 0.1.1
* Fixed ViewportWidget not updating the Viewport.

### 0.1.0
* Added ViewportWidget
* Added ResizeWidget

### 0.0.3
* Fixed function of RangeSlider when using a minimum other than 0. 

### 0.0.2
* Updated SceneComposerBuilder to allow setting the Touchable and Visible properties for widgets.
* Fixed not setting names for various widgets in SceneComposerBuilder.

### 0.0.1
* Included base widgets.