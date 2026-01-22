package org.hau.project.ui.components

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
    _root_ide_package_.org.hau.project.ui.components.HomeDropdownMenu(
        onClickMenuItem = {},
        isExpanded = true
    )
}