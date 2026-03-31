package com.sohaib.composeretrofit.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.sohaib.composeretrofit.helpers.retrofit.models.Product
import com.sohaib.composeretrofit.helpers.retrofit.viewModels.ArticlesViewModel
import com.sohaib.composeretrofit.helpers.utils.HelperUtils.TAG

@Composable
fun MainScreen(viewModel: ArticlesViewModel) {
    val pagingDataState = viewModel.pagingData.collectAsLazyPagingItems()
    RecyclerView(pagingDataState = pagingDataState)
}

@Composable
fun RecyclerView(pagingDataState: LazyPagingItems<Product>) {

    LazyColumn(content = {
        items(pagingDataState.itemCount) {
            val item = pagingDataState.itemSnapshotList.items[it]
            CardItem(product = item)
        }
    })

    when (pagingDataState.loadState.refresh) {
        LoadState.Loading -> {
            Log.d(TAG, "RecyclerView: refresh: called")
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                CircularProgressIndicator(color = Color.Black)
            }
        }

        is LoadState.Error -> {
            Log.e(TAG, "RecyclerView: called")
        }

        else -> {}
    }
    when (pagingDataState.loadState.append) {
        LoadState.Loading -> {
            Log.d(TAG, "RecyclerView: append: called")
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {
                CircularProgressIndicator(color = Color.Black)
            }
        }

        is LoadState.Error -> {
            Log.e(TAG, "RecyclerView: called")
        }

        else -> {}
    }
}

@Composable
fun CardItem(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Sr. ${product.id}",
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}