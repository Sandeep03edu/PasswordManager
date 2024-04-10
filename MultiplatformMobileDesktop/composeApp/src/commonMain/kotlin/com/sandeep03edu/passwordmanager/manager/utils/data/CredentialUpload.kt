package com.sandeep03edu.passwordmanager.manager.utils.data

import com.sandeep03edu.passwordmanager.SharedRes
import dev.icerock.moko.resources.ImageResource

fun getCredentialUploadImage(
    isSynced: Long,
): ImageResource {
    return when (isSynced) {
        0L -> {
            // Not synced
            SharedRes.images.CloudError;
        }
        1L -> {
            // Sycned
            SharedRes.images.CloudDone;
        }
        2L -> {
            // Syncing
            SharedRes.images.CloudSync;
        }
        else -> SharedRes.images.CloudToUpload
    }
}