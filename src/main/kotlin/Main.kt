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


