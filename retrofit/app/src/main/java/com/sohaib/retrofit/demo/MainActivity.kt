package com.sohaib.retrofit.demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sohaib.retrofit.demo.databinding.ActivityMainBinding
import com.sohaib.retrofit.demo.models.Post
import com.sohaib.retrofit.demo.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

private const val TAG = "MyTag"

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fetchPostsUsingCoroutines()
        fetchPostsUsingCallback()
    }

    private fun fetchPostsUsingCoroutines() {
        lifecycleScope.launch {
            val response = try {
                RetrofitInstance.api.getAllPosts()
            } catch (ex: IOException) {
                Log.e(TAG, "getRequest: IOException: $ex")
                return@launch
            } catch (ex: HttpException) {
                Log.e(TAG, "getRequest: HttpException: $ex")
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                Log.d(TAG, "getRequest: Success: ${response.body()}")
            } else {
                Log.e(TAG, "getRequest: Failure: ${response.code()}")
                Log.e(TAG, "getRequest: Failure: ${response.message()}")
                Log.e(TAG, "getRequest: Failure: ${response.errorBody()}")
            }
        }
    }

    private fun fetchPostsUsingCallback() {
        val call = RetrofitInstance.api.getAllPostsCall()
        call.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful && response.body() != null) {
                    Log.d(TAG, "Posts fetched successfully: ${response.body()}")
                } else {
                    Log.e(TAG, "Request failed: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Post>>, throwable: Throwable) {
                Log.e(TAG, "Network request failed: $throwable")
            }
        })
    }
}