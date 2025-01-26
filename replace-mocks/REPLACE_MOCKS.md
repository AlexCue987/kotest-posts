## Using Kotest to Replace Mocks with Test Doubles

### When Our Dependencies Are Functions, We Need Test Doubles, Not Mocks.

Generally, when we do functional programming, and our dependencies are functions, not objects, then when we test our code, we need less mocking, if any at all.
<br/>
<br/>
Personally, while my previous project was Kotlin and SpringBoot, my current gig is completely framework-free. So I'm wiring up my dependencies myself, and know what? Even though the code is covered with tests really well, there are no mocks whatsoever - when my dependencies are functions, I just don't need mocks, all I need is test-double functions.
<br/>
<br/>
 
