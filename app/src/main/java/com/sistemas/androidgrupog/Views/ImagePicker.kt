package com.sistemas.ejemplo2.Views


import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
//import com.sistemas.ejemplo2.permissionConfig.RequestStoragePermission

@Composable
fun ImagePicker(
    onImageSelected: (Uri?) -> Unit,
    onImageRemoved: () -> Unit,
) {

    val launcherPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted: Do something
            Log.d("ExampleScreen","PERMISSION GRANTED")

        } else {
            // Permission Denied: Do something
            Log.d("ExampleScreen","PERMISSION DENIED")
        }
    }
    var selectedImageUri by remember{ mutableStateOf<Uri?>(Uri.parse("")) }
    var imageName by remember { mutableStateOf("") }

    //val launcher = rememberLauncher(onImageSelected,activity)
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            onImageSelected(uri)
            selectedImageUri = uri
        }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        // Display selected image
        var uriAsString = selectedImageUri.toString()
        if ( uriAsString != "") {
            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = selectedImageUri).apply(block = fun ImageRequest.Builder.() {
                    transformations(RoundedCornersTransformation())
                }).build()
            )


            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.large)
                    .clickable { launcher.launch("image/*") }
            )
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Remove image",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onImageRemoved() }
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            )
        } else {
            // Display placeholder with AddAPhoto icon
            val context = LocalContext.current
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium)
                    .clickable {
                        when (PackageManager.PERMISSION_GRANTED) {
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ) -> {
                                // Some works that require permission
                                Log.d("ExampleScreen","Code requires permission")
                            }
                            else -> {
                                // Asking for permission
                                launcherPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                            }
                        }

                        launcher.launch("image/*") },
                color = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Add a photo",
                    tint = Color.White,
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }

    // Display image name input
    /*OutlinedTextField(
        value = imageName,
        onValueChange = { imageName = it },
        label = { MaterialTheme.typography.bodyMedium },
        placeholder = { Text("Enter image name") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        textStyle = MaterialTheme.typography.bodyMedium,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next,
        ),
    )*/

    // Button to submit image
    // Note: You should replace this with your own logic to submit the image along with other data
    /*Button(
        onClick = {
            if (selectedImageUri != null) {
                onImageSelected(selectedImageUri!!)

            } else {

            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text("Submit Image")
    }*/
}