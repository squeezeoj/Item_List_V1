package com.learning.itemlistv1.screens

//---------------------------------------------------------
// Imports
//---------------------------------------------------------
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.learning.itemlistv1.MainViewModel
import com.learning.itemlistv1.NavRoutes

//------------------------------------------------------------------
// Items List Screen
//------------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ItemListScreen(
	navController: NavHostController,
	viewModel: MainViewModel
) {
	//--- View Model State Variables
	var showList by remember { mutableStateOf(viewModel.showItems) }
	var checkedState by remember { mutableStateOf(viewModel.filtering) }
	var textFilter by remember { mutableStateOf(TextFieldValue(viewModel.filterText)) }

	//--- Keyboard Controller
	val keyboardController = LocalSoftwareKeyboardController.current

	//--- Display Content
	Column {

		//----------------------------------------------------------
		// Title
		//----------------------------------------------------------
		Text(
			text = "Item List V0",
			modifier = Modifier.padding(start = 10.dp),
			style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onBackground)
		)
		Spacer(modifier = Modifier.height(5.dp)); Divider(); Spacer(modifier = Modifier.height(5.dp))

		//----------------------------------------------------------
		// Control Row
		//----------------------------------------------------------
		Row {

			//------------------------------------------------------
			// Filter Box
			//------------------------------------------------------
			OutlinedTextField(
				value = textFilter,
				modifier = Modifier.width(100.dp),
				label = { Text(text = "Filter") },
				singleLine = true,
				keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
				keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
				onValueChange = {

					//--- Set Text Here and in View Model
					textFilter = it
					viewModel.filterText = it.text

					//--- Update Show List to Trigger Recomposition
					showList = viewModel.returnShowList()
				}
			)
			Spacer(Modifier.width(10.dp))

			//------------------------------------------------------
			// Filter Checkbox
			//------------------------------------------------------
			Checkbox(
				checked = checkedState,
				onCheckedChange = {

					//--- Set Text Here and in View Model
					checkedState = it
					viewModel.filtering = !viewModel.filtering

					//--- Hide Keyboard
					keyboardController?.hide()

					//--- Update Show List to Trigger Recomposition
					showList = viewModel.returnShowList()		// Updating Show List Triggers Recomposition
				}
			)
			Spacer(Modifier.width(20.dp))

			//------------------------------------------------------
			// Add New Item Button
			//------------------------------------------------------
			Button(onClick = {
				navController.navigate(
					route = NavRoutes.ItemDetailScreen.route
							+ "/" + "0"
							+ "/" + "CREATE"
				)
			}) {
				Text(
					text = "New Item",
					modifier = Modifier
						.height(40.dp)
						.align(Alignment.CenterVertically),
					style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.background)
				)
			}

		}
		Spacer(modifier = Modifier.height(5.dp)); Divider(); Spacer(modifier = Modifier.height(5.dp))

		//------------------------------------------------------------------
		// Lazy Column for Show Items List
		//------------------------------------------------------------------
		LazyColumn {
			items(showList.size) {	item ->
				ClickableText(
					text = AnnotatedString("Item: ${showList[item].title}"),
					modifier = Modifier.padding(start = 10.dp),
					style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground),
					onClick = {
						viewModel.getItemByID(showList[item].id)
						navController.navigate(			// Go to Item Details Screen
							route = NavRoutes.ItemDetailScreen.route
									+ "/" + showList[item].id
									+ "/" + "UPDATE"
						)
					}	// End On Click
				)	// End Clickable Text
				Spacer(modifier = Modifier.height(5.dp)); Divider(); Spacer(modifier = Modifier.height(5.dp))
			}	// End Items
		}	// End Lazy Column

//		//------------------------------------------------------------------
//		// For Each Show Items List
//		//------------------------------------------------------------------
//		showList.forEach {item ->
//			ClickableText(
//				text = AnnotatedString("Item: ${item.title}"),
//				modifier = Modifier.padding(start = 10.dp),
//				style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground),
//				onClick = {
//					viewModel.getItemByID(item.id)
//					navController.navigate(			// Go to Item Details Screen
//						route = NavRoutes.ItemDetailScreen.route
//								+ "/" + item.id
//								+ "/" + "UPDATE"
//					)
//				}	// End On Click
//			)	// End Clickable Text
//			Spacer(modifier = Modifier.height(5.dp)); Divider(); Spacer(modifier = Modifier.height(5.dp))
//		}	// End All Items For Each

	}	// End Column

}	// End Item List Screen