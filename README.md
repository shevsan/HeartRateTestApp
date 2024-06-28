# App Description
It is test task for Boosteight. Android app that measuring heart rate with camera. 
Stack: Kotlin, Compose, Coroutines, MVVM, Clean Arch, Dependecy Injection, Room, Datastore Shared Preferences, CameraX, Lottie.



# Package Structure

```
# App Module
.
├── presentation                  # Store MainActivity / Compose Screens / Theme / VM, etc.
│
├── data                          # Network / Database / DataSource / Repository Impl
│
├── domain                        # Repository Interface / UseCase / Models / Mappers
│
├── di                            # AppModule / DataModule
│
└── common                        # Constants
```
```
# Presentation package
.
├── components                    # Components used around all applications, like some items, top bars
│
├── theme                         # App Colors / App Theme / App Shapes / App Typography
│
└── ui                            # Screens / View Model / Screen Components / Screen Constants
```
# Naming Conventions
```
Class - ExampleExample
```
```
Function - exampleExample
```
```
Composable - ExampleExample
```
```
Composable Preview - ExampleScreenPreview
```
# Compose File Structure
```ExampleScreen.kt```

name for the composable func should be ```ExampleScreen```. 

If a composable is going to contain a lot of code - you should divide it into smaller composable functions ```sections```.

Sections should be called following this pattern: ```*screenName*Screen*SectionName*Section```, example: ```ExampleScreenScrollableSection```

```EexmpleScreen``` contains ```ExampleScreenContent``` wich handles only ui. All ViewModel invocations should happen inside ```ExampleScreen```

For example:
```
@Composable
fun ExampleScreen(modifier: Modifier) {
   val viewModel: ExampleViewModel = hiltViewModel()
   ExampleScreenContent(modifier, viewModel.state.value)
}

@Composable
fun ExampleScreenContent(modifier: Modifier, state: ExampleState) {
    Column(modifier) {
        ExampleScreenTopBarSection(modifier = Modifier)
        ExampleScreenScrollableSection(modifier = Modifier)
    }
}

@Composable
fun ExampleScreenTopBarSection(modifier: Modifier) {
    Row(modifier) {
        //code
    }
}

@Composable
fun ExampleScreenScrollableSection(modifier: Modifier) {
    Column(modifier) {
        //code
    }
}

@Preview
@Composable
fun ExampleScreenPreview() {
    ExampleScreenContent(modifier = Modifier, state = ExampleState.DefaultValue)
}
```
