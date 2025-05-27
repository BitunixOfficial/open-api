package main

import "testing"

func TestGenerateSignature(t *testing.T) {
	type args struct {
		apiKey      string
		secretKey   string
		nonce       string
		timestamp   string
		queryParams string
		body        string
	}
	tests := []struct {
		name string
		args args
		want string
	}{
		{
			name: "test1",
			args: args{
				apiKey:      "11111111111111111",
				secretKey:   "22222222222222222",
				nonce:       "33333333333333333",
				timestamp:   "1747104711262",
				queryParams: "marginCoinUSDT",
				body:        "",
			},
			want: "40e87256554e263e39201a3979469b7fbb640d1c7c41759abf5c6a18f937f922",
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := GenerateSignature(tt.args.apiKey, tt.args.secretKey, tt.args.nonce, tt.args.timestamp, tt.args.queryParams, tt.args.body); got != tt.want {
				t.Errorf("GenerateSignature() = %v, want %v", got, tt.want)
			}
		})
	}
}
