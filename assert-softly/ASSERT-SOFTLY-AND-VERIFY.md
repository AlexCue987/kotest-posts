## Assertions from other libraries are mostly compatible with Kotest

Generally, we can invoke non-kotest assertions inside kotest tests, and they mostly work, but there are some exceptions - this is why this post is needed. 
As an example, here we shall only consider `verify` method from `mockk` library, but the same principle should apply to any other assertions.

### `verify` fails a test, as expected
