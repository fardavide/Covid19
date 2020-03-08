object Module {

    // Test
    const val sharedTest = ":sharedTest"

    // Domain
    const val domain = ":domain"

    // Data
    const val data = ":data"
    const val localData = "$data:local"
    const val remoteData = "$data:remote"

    // Presentation
    const val android = ":android"
    const val commandLine = ":commandLine"
    const val iOs = ":iOs"
    const val web = ":web"
}
