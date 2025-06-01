# Java Streams Guide

## 🔹 What is a Stream?
A **stream** is a sequence of data used for reading from or writing to data sources (files, memory, network).

---

## 🔸 Types of Streams

### 1. Character Streams
- For **text-based data** (e.g., `.txt` files)
- Classes end with: `Reader`, `Writer`
- Example: `InputStreamReader`, `BufferedReader`

### 2. Byte Streams
- For **binary data** (e.g., images, videos)
- Classes end with: `InputStream`, `OutputStream`
- Example: `FileInputStream`, `ObjectOutputStream`

---

## 🔗 Bridge Between Streams

- `InputStreamReader`: Converts byte stream → character stream
- `OutputStreamWriter`: Converts character stream → byte stream

---

## ⚡ Buffering for Performance

Instead of reading/writing one byte/char at a time, use **buffered streams**:
- `BufferedReader` / `BufferedWriter` (character)
- `BufferedInputStream` / `BufferedOutputStream` (byte)

Improves performance by processing **blocks of data**.

---

## 💧 Flushing the Buffer

- Buffered output doesn’t write immediately.
- Data is sent when buffer is full.
- Use `.flush()` to force data out **now**.

Use `.flush()` when:
- You need immediate output
- Before closing the stream
- In real-time communication


## Here is an image for clarification:
The content of the buffer, when full, gets flushed to the `InputStreamReader`, which then gets flushed to the underlying `InputStream`.

---

## Scanner:
The `Scanner` class is used to take input from the console. 
`System.in` is an `InputStream` that is connected to keyboard input.

## Note:
`BufferedWriter.newLine()` 
adds `\n` because `nextLine()` doesn't return a line separator.

The `accept()` method of the `ServerSocket` class waits for a client connection. Once connected, a `Socket` object is returned that can be used to communicate with the client.
When dealing with multiple users, you will need a specific socket for every connection; that's what `accept()` returns.

So, the server socket is just for listening for connections.