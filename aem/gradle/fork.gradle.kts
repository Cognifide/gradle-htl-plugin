import com.neva.gradle.fork.ForkExtension

configure<ForkExtension> {
    properties {
        define(mapOf(
                "adUsername" to { defaultValue = System.getProperty("user.name") },
                "adPassword" to { password() },
                "projectName" to {
                    description = "Artifact 'name' coordinate (lowercase)"
                    validator { lowercased(); alphanumeric() }
                },
                "projectLabel" to { description = "Nice project name (human-readable)" },
                "projectGroup" to {
                    description = "Java package in source code and artifact 'group' coordinate"
                    validator { javaPackage(); notContains("projectName") }
                },
                "aemInstanceType" to {
                    select("local", "remote")
                    description = "local - instance will be created on local file system.\nremote - connecting to remote instance only."
                },
                "aemInstanceRunModes" to { text("nosamplecontent") },
                "aemInstanceJvmOpts" to { text("-server -Xmx1024m -XX:MaxPermSize=256M -Djava.awt.headless=true") },
                "aemInstanceAuthorHttpUrl" to {
                    url("http://localhost:4502")
                    description = "URL for accessing AEM author instance"
                },
                "aemInstancePublishHttpUrl" to {
                    url("http://localhost:4503")
                    description = "URL for accessing AEM publish instance"
                }
        ))
    }
    config {
        cloneFiles()
        moveFiles(mapOf(
                "/com/company/aem/aem" to "/{{projectGroup|substitute('.', '/')}}/{{projectName}}/aem",
                "/aem" to "/{{projectName}}"
        ))
        replaceContents(mapOf(
                "com.cognifide.aem.aem" to "{{projectGroup}}.{{projectName}}.aem",
                "com.cognifide.aem" to "{{projectGroup}}.{{projectName}}",
                "Basic AEM Project" to "{{projectLabel}}",
                "aem" to "{{projectName}}"
        ))
    }
}
