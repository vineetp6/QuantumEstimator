package estimator // Optional but good practice for organizing code

import kotlin.math.log10

// A 'data class' in Kotlin is specifically designed to hold data.
data class ResourceEstimate(
    val codeDistance: Int,
    val physicalPerLogical: Int,
    val totalPhysicalQubits: Int
)

fun calculateHardwareRequirements(
    logicalQubits: Int,
    physicalErrorRate: Double,    // e.g., 0.001 (10^-3)
    targetAlgorithmError: Double  // e.g., 1e-15 (10^-15)
): ResourceEstimate {
    
    // 1. Calculate Code Distance (d)
    // Heuristic: d ≈ 2 * log10(Target Error) / log10(Physical Error)
    val rawDistance = (2 * log10(targetAlgorithmError) / log10(physicalErrorRate)).toInt()
    
    // Surface code distance must be an odd number (3, 5, 7, etc.)
    var d = rawDistance
    if (d % 2 == 0) {
        d += 1
    }
    if (d < 3) d = 3 // Minimum distance for surface code

    // 2. Calculate Physical Qubits per Logical Qubit
    // Formula for surface code overhead: 2 * d^2
    val physicalPerLogical = 2 * (d * d)

    // 3. Calculate Total Physical Qubits required
    val totalPhysical = logicalQubits * physicalPerLogical

    return ResourceEstimate(
        codeDistance = d,
        physicalPerLogical = physicalPerLogical,
        totalPhysicalQubits = totalPhysical
    )
}





// fun calculateHardwareRequirements(
//     logicalQubits: Int,
//     physicalErrorRate: Double,    // e.g., 0.001 (10^-3)
//     targetAlgorithmError: Double  // e.g., 1e-15 (10^-15)
// ): ResourceEstimate {
    // : ResourceEstimate is Kotlin's way of defining the function's return type. 
        // It acts as a contract, telling the compiler exactly what kind of data this function will hand back when it finishes its job.
    // Usually, a math function might just return a single number, like an Int or a Double. 
        // For example:fun add(a: Int, b: Int): Int
        // However, our quantum engine needs to calculate and return three distinct numbers:
           // The code distance
           //The physical qubits per logical qubit
           // The total physical qubits
   // A function can only return one thing. 
        // So, we use the ResourceEstimate data class we created earlier as a "box" to pack those three numbers together.



// data class 
  // If a function only needs to return one number, we don't need a data class. 
     // But since we need three linked results, the data class packages them together.