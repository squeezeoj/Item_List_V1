package com.learning.itemlistv1.screens

//---------------------------------------------------------
// Imports
//---------------------------------------------------------
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.learning.itemlistv1.MainViewModel
import com.learning.itemlistv1.NavRoutes
import com.learning.itemlistv1.models.Item

//------------------------------------------------------------------
// Items Detail Screen
//------------------------------------------------------------------
@Composable
fun ItemDetailScreen(
	navController: NavHostController,
	viewModel: MainViewModel,
	id: Int? = 0,
	crud: String? = "NONE"
) {

	val itemID: String
	var itemTitle: String
	var myItem: Item

	when (crud) {
		//---------------------------------------------------------
		// Create New Item
		//---------------------------------------------------------
		"CREATE" -> {

			Column {

				//--- Item Title
				var textTitle by remember { mutableStateOf(TextFieldValue("")) }
				OutlinedTextField(
					value = textTitle,
					label = { Text(text = "Title") },
					singleLine = true,
					onValueChange = {
						textTitle = it
					}
				)
				itemTitle = textTitle.text

				//--- Create New Item from Parts
				myItem = Item(
					id = (100000..200000).random(),
					title = itemTitle
				)

				//--- Add New Item Button
				Button(
					onClick = {
						navController.navigate(route = NavRoutes.ItemListScreen.route) {
							popUpTo(NavRoutes.ItemListScreen.route)
						}									// Go to Item List Screen
						viewModel.insertItem(myItem)		// Add Item to List
						viewModel.updateShowList()			// Triggers Item List Recompose
					}) {
					Text(
						text = "Submit",
						style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.background),
					)
				}    // End Add New Item Button

			}    // End Create New Item Column

		}
		//---------------------------------------------------------
		// Update Existing Item
		//---------------------------------------------------------
		"UPDATE" -> {

			//--- Make sure id isn't null, because it's not guaranteed
			if (id != null) {

				//--- Populate specific item view model variable
				viewModel.getItemByID(id)

				//--- Get specific item info
				myItem = viewModel.specificItem
				itemID = myItem.id.toString()
				itemTitle = myItem.title

				Column {

					//--- Item ID Text Field (read only)
					OutlinedTextField(
						value = itemID,
						enabled = false,
						readOnly = true,
						label = { Text(text = "ID") },
						singleLine = true,
						onValueChange = {  }
					)

					//--- Item Title Text Field
					var textTitle by remember { mutableStateOf(TextFieldValue(itemTitle)) }
					OutlinedTextField(
						value = textTitle,
						label = { Text(text = "Title") },
						singleLine = true,
						onValueChange = {
							textTitle = it
						}
					)
					itemTitle = textTitle.text

					//--- Create Updated Item from Parts
					myItem = Item(
						id = itemID.toInt(),
						title = itemTitle
					)

					//--- Update Existing Item Button
					Button(
						onClick = {
							navController.navigate(route = NavRoutes.ItemListScreen.route) {
								popUpTo(NavRoutes.ItemListScreen.route)
							}									// Go to Item List Screen
							viewModel.updateItem(myItem)		// Update Item in List
							viewModel.updateShowList()			// Triggers Item List Recompose
						}) {
						Text(
							text = "Submit",
							style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.background),
						)
					}    // End Update Existing Item Button

					Spacer(Modifier.height(10.dp))

					//--- Delete this Item Button
					Button(
						onClick = {
							navController.navigate(route = NavRoutes.ItemListScreen.route) {
								popUpTo(NavRoutes.ItemListScreen.route)
							}									// Go to Item List Screen
							viewModel.deleteItem(myItem)		// Delete Item from List
							viewModel.updateShowList()			// Triggers Item List Recompose
						}) {
						Text(
							text = "Delete this Item",
							style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.background),
						)
					}    // End Update Existing Item Button

				}   // End Column

			}   // End If Not Null

		}
		//---------------------------------------------------------
		// Else
		//---------------------------------------------------------
		else -> {
			// Nothing Else To Do
		}
	}   // End When

}   	// End Item Details Screen
