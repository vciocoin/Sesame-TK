package fansirsqi.xposed.sesame.hook

import de.robv.android.xposed.XposedBridge
import fansirsqi.xposed.sesame.data.General
import fansirsqi.xposed.sesame.data.ViewAppInfo
import io.github.libxposed.api.XposedModule
import io.github.libxposed.api.XposedModuleInterface


class LspEntry(
    base: io.github.libxposed.api.XposedInterface,
    param: XposedModuleInterface.ModuleLoadedParam
) : XposedModule(base, param) {
    var customHooker: ApplicationHook? = null
    private val processName = param.processName

    init {
        XposedBridge.log("LspEntry: Initialized for process $processName")
        val frameWorkName = base.frameworkName
        val frameworkVersion = base.frameworkVersion
        ViewAppInfo.xpFrameworkVersion = "$frameWorkName $frameworkVersion"
        XposedBridge.log("LspEntry: Framework name: $frameWorkName, version: $frameworkVersion")
    }

    override fun onPackageLoaded(param: XposedModuleInterface.PackageLoadedParam) {
        customHooker = ApplicationHook()
        if (General.PACKAGE_NAME == processName) {
            customHooker?.loadPackage(param)
        } else if (General.MODULE_PACKAGE_NAME == processName) {
            customHooker?.loadModelPackage(param)
        }
    }
}