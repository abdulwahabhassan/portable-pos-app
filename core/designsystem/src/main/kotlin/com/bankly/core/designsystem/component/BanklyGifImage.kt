package com.bankly.core.designsystem.component

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.bankly.core.designsystem.R
import com.bankly.core.designsystem.theme.BanklyTheme

@Composable
fun BanklyGifImage(
    modifier: Modifier = Modifier,
    drawable: Int,
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(data = drawable).apply(block = {
                size(Size.ORIGINAL)
            }).build(),
            imageLoader = imageLoader,
        ),
        contentDescription = null,
        modifier = modifier,
    )
}

@Composable
@Preview(showBackground = true)
fun BanklyGifImagePreview() {
    BanklyTheme {
        BanklyGifImage(drawable = R.drawable.ic_loading)
    }
}
