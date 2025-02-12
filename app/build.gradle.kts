plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.kotlin.compose)
	id("kotlin-kapt")
	alias(libs.plugins.hilt)
}

android {
	namespace = "com.yrtelf.chatsocket"
	compileSdk = 35

	defaultConfig {
		applicationId = "com.yrtelf.chatsocket"
		minSdk = 26
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}
	buildFeatures {
		compose = true
	}
}

dependencies {
	implementation(libs.accompanist.systemuicontroller)

	implementation(libs.coil.compose)

	implementation(libs.google.gson)

	implementation(libs.androidx.room.runtime)
	kapt(libs.androidx.room.compiler)

	// Kotlin Coroutines ile Room kullanımı için
	implementation(libs.androidx.room.ktx)

	implementation(libs.okhttp)
	implementation(libs.okhttp.sse)

	// Hilt
	implementation(libs.hilt.android)
	kapt(libs.hilt.compiler)

	// Hilt Navigation for Jetpack Compose
	implementation(libs.androidx.hilt.navigation.compose)

	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.androidx.activity.compose)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.ui)
	implementation(libs.androidx.ui.graphics)
	implementation(libs.androidx.ui.tooling.preview)
	implementation(libs.androidx.material3)
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.ui.test.junit4)
	debugImplementation(libs.androidx.ui.tooling)
	debugImplementation(libs.androidx.ui.test.manifest)
}