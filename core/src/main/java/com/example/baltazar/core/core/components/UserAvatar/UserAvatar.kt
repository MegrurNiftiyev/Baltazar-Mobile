package com.example.baltazar.core.core.components.UserAvatar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.baltazar.core.R
import com.example.baltazar.core.domain.model.User

@Composable
fun UserAvatar(
    user: User,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp
) {
    val avatarUrl = user.avatarUrl

    if (avatarUrl.isNullOrBlank()) {
        Image(
            painter = painterResource(id = R.drawable.default_user_photo),
            contentDescription = user.name,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(size)
                .clip(CircleShape)
        )
    } else {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(avatarUrl)
                .crossfade(true)
                .error(R.drawable.default_user_photo)
                .placeholder(R.drawable.default_user_photo)
                .fallback(R.drawable.default_user_photo)
                .build(),
            contentDescription = user.name,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(size)
                .clip(CircleShape)
        )
    }
}
