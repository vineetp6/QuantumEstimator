import androidx.compose.foundation.layout.* // Imports Row, Column, Spacer, padding, etc.
import androidx.compose.material3.* // Imports Card, Slider, Text, MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import estimator.calculateHardwareRequirements
import kotlin.math.roundToInt

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Quantum Resource Estimator") {
        MaterialTheme {
            EstimatorApp()
        }
    }
}

@Composable
fun EstimatorApp() {
    // --- 1. STATE VARIABLES ---
    // Sliders in Compose require Float values, so we store state as Float.
    var logicalQubitsFloat by remember { mutableFloatStateOf(4000f) }
    var physicalErrorRate by remember { mutableFloatStateOf(0.001f) }
    val targetAlgorithmError = 1e-15

    // Convert the slider's Float back to an Int for our math engine.
    val logicalQubits = logicalQubitsFloat.roundToInt()

    // --- 2. THE CALCULATION ---
    // Because this depends on 'logicalQubitsFloat' and 'physicalErrorRate', 
    // Compose automatically recalculates this every time a slider moves.
    val result = calculateHardwareRequirements(
        logicalQubits, 
        physicalErrorRate.toDouble(), // Engine expects Double
        targetAlgorithmError
    )

    // --- 3. THE UI LAYOUT ---
    // fillMaxSize ensures the Column takes up the whole application window.
    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        
        // --- INPUT SECTION ---
        // Card creates a clean, elevated container with rounded corners.
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Algorithm Requirements", style = MaterialTheme.typography.titleMedium)
                
                // Spacer creates empty vertical or horizontal space between elements.
                Spacer(modifier = Modifier.height(16.dp)) 

                // Logical Qubits Text & Slider
                Text(text = "Logical Qubits: $logicalQubits")
                Slider(
                    value = logicalQubitsFloat,
                    // onValueChange is triggered every microsecond the user drags the slider.
                    // We take the 'newValue' and inject it into our state variable.
                    onValueChange = { newValue -> logicalQubitsFloat = newValue },
                    valueRange = 10f..10000f // Sets the min and max bounds for the slider
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Physical Error Rate Text & Slider
                // String.format ensures the float displays nicely (e.g., 0.0010) instead of messy scientific notation.
                Text(text = "Physical Error Rate: ${String.format("%.4f", physicalErrorRate)}")
                Slider(
                    value = physicalErrorRate,
                    onValueChange = { newValue -> physicalErrorRate = newValue },
                    valueRange = 0.0001f..0.01f
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- RESULT SECTION ---
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Hardware Estimation", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(text = "Code Distance (d): ${result.codeDistance}")
                Text(text = "Physical Qubits per Logical: ${result.physicalPerLogical}")
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Highlighting the final answer using a larger font and the app's primary theme color.
                Text(
                    text = "Total Physical Qubits: ${result.totalPhysicalQubits}", 
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}



//State Management: 
  // In Jetpack Compose, the UI is "reactive." 
    // This means we don't manually tell a text box to update its text. 
    //Instead, we declare a "state" variable. 
    //When that variable changes, Compose automatically redraws only the parts of the screen that use it.
  
  // We will use remember (so the app doesn't forget the value when it redraws) and mutableStateOf (to hold the value).




  // // 'by remember' tells Compose to track these value and redraw the UI if it changes.
        // var logicalQubits by remember { mutableIntStateOf(4000) }


  // In Jetpack Compose, we break the UI down into reusable blocks called "Composables". 
     // Instead of stuffing everything inside fun main(), we will create a dedicated function for our app's screen.





// Application Shell
// fun main() = application {
//     Window(onCloseRequest = ::exitApplication, title = "Quantum Resource Estimator") {
//         MaterialTheme {
//             EstimatorApp()
//         }
//     }
// }
    // application { ... }: This tells Kotlin to start a background lifecycle for a desktop app.
      //  It listens for system events like minimizing or closing.
    // Window(...): This creates the actual physical window on your Windows 11 desktop. 
        // onCloseRequest = ::exitApplication ensures that when you click the 'X' in the top right, the program fully shuts down in the background.
    // MaterialTheme: This wraps your UI in Google's Material Design system, giving you access to standard fonts, colours, and shapes out of the box so you don't have to style everything from scratch.





// Composable Function & State Management
//   @Composable
//   fun EstimatorApp() {
//     var logicalQubitsFloat by remember { mutableFloatStateOf(4000f) }
//     var physicalErrorRate by remember { mutableFloatStateOf(0.001f) }
     // @Composable: This annotation transforms a standard Kotlin function into a UI generator. 
          // It tells the compiler, "This function draws pixels on the screen."
     // remember: This is Jetpack Compose's memory. 
     //If you just used a normal variable (var x = 4000), every time the screen redrew itself, the variable would reset to 4000.
       // remember tells the app to hold onto the user's changes across screen redraws.
     // mutableFloatStateOf: This creates an active tracker. 
         // When a user drags a slider, this value changes. 
         // Compose is constantly watching this specific variable, and the millisecond it changes, Compose automatically redraws only the UI components that rely on it. 





// Layouts and Modifiers

  // Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
     //Card(modifier = Modifier.fillMaxWidth()) {
     
     // Column: The foundational building block of Compose layouts. 
      // It stacks everything inside it vertically. Without it, all your text and sliders would draw directly on top of each other in the top-left corner.
     // Modifier: This is how you tweak a component's size, spacing, and behaviour.
     // padding(16.dp) adds 16 density-independent pixels of breathing room inside the edges.
     // fillMaxSize() tells the Column to stretch out and take up the entire desktop window.


// Slider(
//     value = logicalQubitsFloat,
//     onValueChange = { newValue -> logicalQubitsFloat = newValue },
//     valueRange = 10f..10000f
// )
  // value: Tells the slider where its drag handle should currently sit.
  // onValueChange: This is a listener. As you drag the mouse, it rapidly fires, passing the new position as newValue.
   //  We instantly push that newValue back into our logicalQubitsFloat state tracker. 
   //This completes the loop, causing the screen to instantly redraw the new math results.
  // valueRange: Sets the minimum and maximum boundaries for the slider track.
  