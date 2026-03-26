# Retrofit Sample 🚀

A small Android demo showing how to call the `GET /posts` endpoint using Retrofit and print the response to Logcat using the JSONPlaceholder fake REST API.

## API Endpoint

```
https://jsonplaceholder.typicode.com/posts
```

## Behavior

When the app launches, it makes the same API request using two different approaches:

1. **Coroutine (`suspend`) approach**
2. **Callback (`enqueue`) approach**

Both responses are logged in **Logcat** for demonstration purposes.

## Purpose 🎯

This project demonstrates:

- Making network requests with **Retrofit**
- Using **Coroutines** for asynchronous API calls
- Using traditional **callback-based networking**
- Logging API responses for debugging

## Note ⚡

In modern Android development, the **Coroutine approach is preferred** because it:

- Produces cleaner and more readable code
- Avoids callback nesting
- Works seamlessly with **ViewModel, Flow, and Clean Architecture**

Callbacks are still useful when working with **legacy code or certain libraries**.