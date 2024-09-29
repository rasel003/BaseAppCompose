package com.rasel.baseappcompose.designsystem.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.webtoonscorp.android.readmore.foundation.ReadMoreTextOverflow
import com.webtoonscorp.android.readmore.foundation.ToggleArea
import com.webtoonscorp.android.readmore.material3.ReadMoreText

@Composable
fun ReadMoreTextCompose(
    modifier: Modifier = Modifier,
    textString: String,
    expandedState: Boolean,
    onExpandedChange: (Boolean) -> Unit,
) {
    ReadMoreText(
        text = textString,
        expanded = expandedState,
        onExpandedChange = onExpandedChange,
        modifier = modifier,

        // // // Read More and Read Less are required to written // // //

        // Read More
        readMoreText = "read more",     // Visible at the end of the text
        readMoreColor = Color.Black,
        readMoreFontWeight = FontWeight.Bold,
        readMoreMaxLines = 3,
        readMoreOverflow = ReadMoreTextOverflow.Ellipsis,
        // you can try changing several other properties like fontfamily, textsize, etc. with readMore as prefix

        // Read Less
        readLessText = "read less",      // Visible at the end of the text
        readLessColor = Color.Black,
        readLessFontWeight = FontWeight.Bold,
        // you can try changing several other properties like fontfamily, textsize, etc. with readLess as prefix


        /* If you want the user to click anywhere on the text and it should expand and collapse then use "ToggleArea.All"
         else If you want the user to click only the "read more" or "read less" text and then it should expand and collapse then use "ToggleArea.More" */
        toggleArea = ToggleArea.More   // ToggleArea.All
    )
}