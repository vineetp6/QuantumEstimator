import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.* // Imports remember, mutableStateOf, getValue, setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import estimator.calculateHardwareRequirements

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Quantum Resource Estimator") {
        MaterialTheme {
            EstimatorApp() // We moved the UI into its own Composable function
        }
    }
}

@Composable
fun EstimatorApp() {
    // 1. STATE VARIABLES
    // 'by remember' tells Compose to track these values and redraw the UI if they change.
    var logicalQubits by remember { mutableIntStateOf(4000) }
    var physicalErrorRate by remember { mutableDoubleStateOf(0.001) }
    val targetAlgorithmError = 1e-15 // Keeping this static for now

    // 2. THE CALCULATION
    // This runs automatically whenever logicalQubits or physicalErrorRate changes.
    val result = calculateHardwareRequirements(logicalQubits, physicalErrorRate, targetAlgorithmError)

    // 3. THE UI LAYOUT
    // A Column stacks items vertically. Modifier.padding adds space around the edges.
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "INPUTS", style = MaterialTheme.typography.titleMedium)
        Text(text = "Logical Qubits: $logicalQubits")
        Text(text = "Physical Error Rate: $physicalErrorRate")
        
        Text(text = "\nRESULTS", style = MaterialTheme.typography.titleMedium)
        Text(text = "Code Distance needed: ${result.codeDistance}")
        Text(text = "Physical qubits per logical: ${result.physicalPerLogical}")
        Text(text = "Total Physical Qubits: ${result.totalPhysicalQubits}")
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


