import groovy.lang.Closure
import org.gradle.api.Action
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.ComponentMetadataHandler
import org.gradle.api.artifacts.dsl.ComponentModuleMetadataHandler
import org.gradle.api.artifacts.dsl.DependencyConstraintHandler
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.query.ArtifactResolutionQuery
import org.gradle.api.artifacts.transform.TransformAction
import org.gradle.api.artifacts.transform.TransformParameters
import org.gradle.api.artifacts.transform.TransformSpec
import org.gradle.api.artifacts.transform.VariantTransform
import org.gradle.api.artifacts.type.ArtifactTypeContainer
import org.gradle.api.attributes.AttributesSchema
import org.gradle.api.plugins.ExtensionContainer

val dependencyHandler = object : DependencyHandler {
    override fun platform(notation: Any): Dependency {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun platform(notation: Any, configureAction: Action<in Dependency>): Dependency {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun create(dependencyNotation: Any): Dependency {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun create(dependencyNotation: Any, configureClosure: Closure<*>): Dependency {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun testFixtures(notation: Any): Dependency {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun testFixtures(notation: Any, configureAction: Action<in Dependency>): Dependency {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getExtensions(): ExtensionContainer {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun gradleApi(): Dependency {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun components(configureAction: Action<in ComponentMetadataHandler>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add(configurationName: String, dependencyNotation: Any): Dependency? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add(configurationName: String, dependencyNotation: Any, configureClosure: Closure<*>): Dependency {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createArtifactResolutionQuery(): ArtifactResolutionQuery {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getModules(): ComponentModuleMetadataHandler {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getArtifactTypes(): ArtifactTypeContainer {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun modules(configureAction: Action<in ComponentModuleMetadataHandler>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun artifactTypes(configureAction: Action<in ArtifactTypeContainer>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun localGroovy(): Dependency {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getComponents(): ComponentMetadataHandler {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun enforcedPlatform(notation: Any): Dependency {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun enforcedPlatform(notation: Any, configureAction: Action<in Dependency>): Dependency {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getConstraints(): DependencyConstraintHandler {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun registerTransform(registrationAction: Action<in VariantTransform>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T : TransformParameters?> registerTransform(
        actionType: Class<out TransformAction<T>>,
        registrationAction: Action<in TransformSpec<T>>
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun gradleTestKit(): Dependency {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun constraints(configureAction: Action<in DependencyConstraintHandler>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAttributesSchema(): AttributesSchema {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun module(notation: Any): Dependency {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun module(notation: Any, configureClosure: Closure<*>): Dependency {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun project(notation: MutableMap<String, *>): Dependency {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun attributesSchema(configureAction: Action<in AttributesSchema>): AttributesSchema {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}