package main

import (
	"testing"
)

// test demo entry file
func TestWsFuturePublic(t *testing.T) {
	WsPublicExampleUsage()
}

func TestWsFuturePrivate(t *testing.T) {
	WsPrivateExampleUsage()
}

func TestHttpFuturePublic(t *testing.T) {
	HttpPublicExampleUsage()
}

func TestHttpFuturePrivate(t *testing.T) {
	HttpPrivateExampleUsage()
}
