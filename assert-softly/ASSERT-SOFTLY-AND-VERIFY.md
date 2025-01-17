## Assertions from other libraries are mostly compatible with Kotest

Generally, we can invoke non-kotest assertions inside kotest tests, and they mostly work, but there are some exceptions. 
In this post we shall only consider `verify` method from `mockk` library, but the same principle should apply to other assertions as well.

### `verify` fails a test, as expected
