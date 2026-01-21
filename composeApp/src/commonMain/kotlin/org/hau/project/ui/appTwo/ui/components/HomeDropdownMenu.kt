package org.hau.project.ui.appTwo.ui.components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeDropdownMenu(
    onClickMenuItem: ()-> Unit,
    isExpanded: Boolean
){
    //var isExpanded by remember{ mutableStateOf(false) }

}

@Composable
@Preview(showBackground = true, widthDp = 400, heightDp = 600)
fun HomeDropdownMenuPreview(){
    HomeDropdownMenu(
        onClickMenuItem = {},
        isExpanded = true
    )
}