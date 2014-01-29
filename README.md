# event-based-arch

## a friend recently asked me some questions about testing, which eventually led to the email below

So, it's very easy to say that pure functions are easier to test and understand, but my team always ended up with state intermingled with our functions. So, we recently switched to using jetlang and moving to an event based model. Now we have 3 namespaces: e, s, m.

- e: external interactions, e.g. incoming messages via jetlang remoting or webbit.
- s: state
- m: model

Each e, s, & m has a 'coordinator namespace which subscribes to internal events (via jetlang) and calls pure functions, passing in dereferenced state where necessary. The coordinator then takes the results of the pure functions and calls some side effect function.

- e: publish to web-socket, log to file, publish to jetlang remoting
- s: publish a state-updated internal event to jetlang
- m: publish a model-updated internal event to jetlang

so, we get some constants that are pretty easy to reason about: Only deref state in coordinators. An external event can change many things, e.g. new market data will have a subscriber in s that simply records the new market data, a subscriber in m that calculates some derivative state, & a subscriber in e that updates any views that care about the market data in the views. We also use macros to define all of the internal event subscriptions, so that you're limited to subscribing to one event with one side effect event and one pure function. If you need two side effects to happen, you create 2 subscriptions that call 2 pure functions that are specific to the side effect.

All the pure functions are very easy to test and need almost no setup (which makes the tests much cleaner).

Is that clear at all? 

## my friend responded with the following

Sounds very interesting.  How about a bit of sample code?

## thus, this repo was born.

Keep in mind, the use of an atom as a channel is for simplicity, it's not a recommendation.
