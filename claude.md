package net.mikelythgoe.springbootonlygrpc;

I've got a Git repo at https://github.com/mlythgoe/spring-boot-only-grpc - can you review the code

19:43

Ran 2 commands, searched the web
Ran 2 commands, searched the web
Unfortunately, GitHub isn't reachable from this environment (it's not in the allowed network domains). However, I can still review your code if you share it here directly.

A few options:

Paste the code – share individual files or the whole project inline and I'll review it.
Upload files – you can upload files directly to this chat (zip or individual files).
Share key files – if it's a large project, the most impactful files to review would be:
build.gradle / pom.xml (dependencies & config)
Your .proto file(s)
Service implementation classes
Spring Boot application/configuration classes
What works best for you?

try again

19:46

Ran a command, searched the web
Ran a command, searched the web
I can see the repo structure. Let me fetch the key files.


Searched the web
Searched the web
Let me look at the source tree by navigating from the repo page links I already have.


Searched the web
Searched the web
I was able to fetch the pom.xml successfully. Let me now try fetching the Java source files directly using the blob URL pattern that worked.


Searched the web
Searched the web
I got the pom.xml in full. I can't navigate deeper into the src/ tree via the GitHub HTML (robots.txt blocks tree pages), but I can try fetching individual source files by guessing the paths. Let me try the most likely ones.


Searched the web
Searched the web
I was able to retrieve the full pom.xml. Unfortunately GitHub's robots.txt blocks fetching the src/ tree pages, so I can only access individual files I can guess the path of. I have enough from the pom.xml to give you a solid review though. Let me share what I've found.

Code Review: spring-boot-only-grpc
Based on the pom.xml I was able to fully read, here's my assessment:

✅ What's well done
Modern, first-party Spring gRPC stack. You're using org.springframework.grpc:spring-grpc-server-spring-boot-starter (the official Spring project, not the older third-party net.devh starter), which is the right choice for new projects. The spring-grpc-dependencies BOM is used correctly for dependency version management.

Modern protobuf toolchain. io.github.ascopes:protobuf-maven-plugin (v4.0.3) is a solid modern alternative to the older xolstice plugin — it handles cross-platform protoc download automatically without the os-maven-plugin extension.

grpc-services included. Including io.grpc:grpc-services gives you the gRPC health check and reflection services out of the box — important for production readiness and tooling like grpcurl or Bruno.

@generated=omit option on protoc-gen-grpc-java. Nice touch — this suppresses the @javax.annotation.Generated annotation from generated code, avoiding a legacy Java EE dependency.

⚠️ Issues & Suggestions
1. Spring Boot version looks incorrect (4.0.5) Spring Boot 4.x doesn't exist yet. The current stable release is 3.x. This is likely a typo — 3.4.5 perhaps? The Spring gRPC note confirms spring-grpc 1.0.x supports Spring Boot 4.1.x, so if this is intentional as a bleeding-edge setup, double-check compatibility, but Spring Boot 4.x has not been released as of now.

2. Java 25 (<java.version>25</java.version>) Java 25 is not yet released (Java 24 is the current latest). This might be a placeholder or error; 21 (the current LTS) or 24 would be more appropriate.

3. <n> instead of <name> in POM

xml
<n>spring-boot-only-grpc</n>
This should be <name>spring-boot-only-grpc</name>. The <n> tag is unrecognised by Maven and will be silently ignored. Not a build-breaking issue, but it suggests the POM was partially hand-edited.

4. Empty <license>, <developer>, and <scm> blocks These are fine for a personal/demo project, but if this is intended to be published or shared, you'd want to fill them in or remove the empty stubs.

5. No spring-boot-starter-actuator For a gRPC service running in any real environment, adding Actuator (and the health check integration with grpc-services) is highly recommended for liveness/readiness probes.

6. Bruno collection present Good practice including API testing collections alongside the code. Just make sure the Bruno collection files don't accidentally contain any credentials or sensitive environment values.

Summary
Area	Assessment
Dependency management	✅ Clean BOM usage
gRPC library choice	✅ Correct official Spring gRPC
Protobuf plugin	✅ Modern & cross-platform
Java/Spring Boot version	⚠️ Likely incorrect (25 / 4.0.5)
POM correctness	⚠️ <n> typo
Observability	⚠️ No Actuator
If you can share the Java source files (service implementation, .proto file, application.properties), I can review the actual service logic as well.

Spring Boot 4 DOES exist
Java 25 DOES exist

19:50
You're right, my apologies! Both Spring Boot 4 and Java 25 are cutting-edge but very real — I should have searched before pushing back on that. Let me verify.


Searched the web
Searched the web
You're absolutely right, and I apologise for the pushback. Spring Boot 4.0.0 was released on November 20, 2025
Spring
, and it includes first-class support for Java 25
Spring
. My training data clearly hadn't caught up with those releases. That was a mistake on my part — I should have searched before confidently correcting you.

So to revise my review: the <java.version>25</java.version> and <parent>spring-boot-starter-parent:4.0.5</parent> are both perfectly valid. The version choices are actually quite forward-thinking — you're on the latest Spring Boot 4 line with a modern Java release.

One note: Spring Boot 4.0.1 was released on December 18, 2025
Spring
, and based on the release list I can see the 4.x line has continued to patch releases since. You're on 4.0.5 which appears to be a recent patch — good to see you're keeping dependencies current.

The remaining observations from my review still stand — the <n> typo instead of <name>, the empty <license>/<developer>/<scm> stubs, and the lack of Actuator. Would you like me to look at the Java source files if you can share them?

yes please, look at the source files

19:51
I can see the repo has a src/ directory but GitHub's robots.txt blocks tree browsing. Let me try to guess the likely file paths based on the package name net.mikelythgoe.springbootonlygrpc from the POM.


Searched the web
Searched the web
The issue is that GitHub only allows fetching pages that were linked from a page I've already fetched. The src/ tree was blocked by robots.txt, so I can't follow links into it. The pom.xml blob URL worked because it was directly linked from the repo home page I fetched first.

The best options to get around this are:

Share the files here directly — paste the contents or upload them and I'll review everything
Tell me the exact file paths — if you list the files in src/, I can try fetching each blob URL directly since I now have the repo page in context
Make the repo browsable — the robots.txt restriction is GitHub's, not yours, so there's nothing to change on your end unfortunately
What would work best?

the pom file DOES have name not 'n', are you sure you meant what you said?

20:16
You're right to challenge me on that. Let me look at the raw POM content again carefully.

Looking back at what I fetched, the POM actually contained:

xml
<n>spring-boot-only-grpc</n>
That's what was returned in the page content I retrieved. However, I should be cautious — GitHub renders HTML from XML, and it's entirely possible that the <name> tag was stripped or mangled during HTML rendering/extraction, leaving just <n>. The ame part of <name> could have been consumed as an HTML attribute or entity of some kind during the markdown extraction process.

So in practice, your POM almost certainly has <name> and I was wrong to flag it as a genuine issue — it was likely a rendering artefact from how I fetched the page, not a real bug in your code. Apologies for the false alarm on that one.

No problem, nothing is ever as easy as you think is it? ;)

20:17
Ha, absolutely not! Especially when it comes to web scraping and HTML parsing — there's always something ready to trip you up. In this case it was GitHub's robots.txt blocking the tree pages, HTML mangling XML tags during extraction, and me confidently wrong about Spring Boot 4 and Java 25 for good measure! 😄

If you can share or paste the source files I'd love to give the actual Java code a proper review.

package net.mikelythgoe.springbootonlygrpc;

import io.grpc.stub.StreamObserver;
import net.mikelythgoe.springbootonlygrpc.proto.HelloReply;
import net.mikelythgoe.springbootonlygrpc.proto.HelloRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
class GrpcServerService extends net.mikelythgoe.springbootonlygrpc.proto.SimpleGrpc.SimpleImplBase {

    private static final Log log = LogFactory.getLog(GrpcServerService.class);

    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        // validate request
        log.info("Hello " + req.getName());
        if (req.getName().startsWith("error")) {
            throw new IllegalArgumentException("Bad name: " + req.getName());
        }
        if (req.getName().startsWith("internal")) {
            throw new RuntimeException();
        }
        // Create a response
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello ==> " + req.getName()).build();
        // Send the response
        responseObserver.onNext(reply);
        // Mark the end of the stream
        responseObserver.onCompleted();
    }

    @Override
    public void streamHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        log.info("Hello " + req.getName());
        int count = 0;
        while (count < 10) {
            HelloReply reply = HelloReply.newBuilder().setMessage("Hello(" + count + ") ==> " + req.getName()).build();
            responseObserver.onNext(reply);
            count++;
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                responseObserver.onError(e);
                return;
            }
        }
        responseObserver.onCompleted();
    }
}
Show more
20:18
Good, thanks! Here's my review of the service implementation:

✅ What's good
Both unary and server-streaming RPCs implemented — nice to see sayHello and streamHello side by side, it makes the project a useful reference for both patterns.

Interrupt handling in streamHello is correct — restoring the interrupt flag with Thread.currentThread().interrupt() before calling responseObserver.onError(e) and returning is exactly the right pattern. A lot of people get this wrong.

@Service instead of a custom @GrpcService annotation — this is the correct approach for the official Spring gRPC starter (as opposed to the older net.devh starter which used @GrpcService).

⚠️ Issues & Suggestions
1. Exception handling in sayHello is problematic

Throwing raw Java exceptions from a gRPC service method is not the right approach. gRPC has its own status/error model, and unhandled exceptions will result in an UNKNOWN status being returned to the client with no useful message. You should use responseObserver.onError() with a StatusRuntimeException:

java
if (req.getName().startsWith("error")) {
responseObserver.onError(
Status.INVALID_ARGUMENT.withDescription("Bad name: " + req.getName())
.asRuntimeException());
return;
}
if (req.getName().startsWith("internal")) {
responseObserver.onError(
Status.INTERNAL.asRuntimeException());
return;
}
2. No validation that name is non-null/empty

req.getName() could return an empty string (proto3 default). Worth guarding against with a check before the startsWith calls.

3. Thread.sleep in streamHello blocks a gRPC thread

gRPC uses a thread pool, and sleeping on one of those threads for 10 seconds (10 × 1s) ties up a worker. For a demo this is fine, but worth a comment noting it's illustrative only. In production you'd want a scheduled/async approach.

4. Logging uses string concatenation

java
log.info("Hello " + req.getName());
Prefer a guard or parameterised logging to avoid unnecessary string construction:

java
if (log.isInfoEnabled()) {
log.info("Hello " + req.getName());
}
Or switch to SLF4J's Logger which supports log.info("Hello {}", req.getName()) natively.

5. streamHello has no error/input validation

Unlike sayHello, streamHello does no validation at all before starting to stream. Worth being consistent.

Summary
Area	Assessment
Streaming implementation	✅ Correct pattern
Interrupt handling	✅ Done right
Error handling	⚠️ Should use StatusRuntimeException
Input validation	⚠️ Incomplete / inconsistent
Thread usage	⚠️ Fine for demo, not production
Logging	⚠️ Minor — string concat
The bones are solid and it's a good reference project. The main thing worth fixing for anyone using this as a template is the error handling — throwing raw exceptions is a pattern that will confuse gRPC clients in the real world.

good advice, cheers

20:19
Glad it was useful! It's a nice clean project — especially good as a reference for the official Spring gRPC starter which doesn't have as many examples out there as the older net.devh one. The streaming implementation with correct interrupt handling is definitely something people will benefit from seeing done properly.

If you do share the .proto file or the Bruno collection at some point, happy to take a look at those too. 👍

Here's the proto file:

syntax = "proto3";

option java_multiple_files = true;
option java_package = "net.mikelythgoe.springbootonlygrpc.proto";
option java_outer_classname = "HelloWorldProto";

// The greeting service definition.
service Simple { // a class named SimpleGrpc (in package 'net.mikelythgoe.springbootonlygrpc.proto' will be created for this
// Sends a greeting
rpc SayHello(HelloRequest) returns (HelloReply) {}
rpc StreamHello(HelloRequest) returns (stream HelloReply) {}
}

// The request message containing the user's name.
message HelloRequest {
string name = 1;
}

// The response message containing the greetings
message HelloReply {
string message = 1;
}
Show more
20:19
Nice and clean! A few observations:

✅ What's good
Good use of comments — the note explaining that service Simple generates SimpleGrpc is genuinely helpful for anyone new to protobuf codegen who might be confused by the naming.

java_multiple_files = true — correct practice, keeps the generated classes as separate files rather than nested inside the outer class.

java_outer_classname set explicitly — good hygiene, avoids any ambiguity in the generated outer class name.

Streaming return type correctly defined — returns (stream HelloReply) correctly matches the streamHello server-side streaming implementation.

⚠️ Suggestions
1. No package defined

There's no package directive (as distinct from java_package). It's best practice to always set one:

proto
package net.mikelythgoe.springbootonlygrpc;
Without it you can get name conflicts if you ever import other proto files.

2. Only one field per message

Fine for a demo, but worth noting that HelloRequest with just name and HelloReply with just message are very minimal. If this is a reference project, adding a second field to at least one message would demonstrate field numbering and multiple fields to readers.

3. No client-streaming or bidirectional streaming examples

You've got unary and server-streaming covered. For a reference project it might be worth adding:

proto
rpc ClientStreamHello(stream HelloRequest) returns (HelloReply) {}
rpc BidiHello(stream HelloRequest) returns (stream HelloReply) {}
Summary
Area	Assessment
File structure options	✅ Correct
Streaming definition	✅ Matches implementation
Proto package	⚠️ Missing
Scope for demo	⚠️ Very minimal — intentional?
Overall it's clean and correct. The missing package directive is the only real thing worth fixing.



Claude works directly with your codebase

Let Claude edit files, run commands, and ship changes right inside your terminal or IDE.




Claude is AI and can make mistakes. Please double-check responses.
