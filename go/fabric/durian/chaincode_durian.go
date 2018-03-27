package main

import (
	"encoding/json"
	"errors"
	"fmt"
	"strconv"
	"github.com/hyperledger/fabric/core/chaincode/shim"
)

type DurianChaincode struct {
}

type Durian struct {
	No string `json:"no"`
	Status string `json:"status"`
}

func (cc *DurianChaincode) Init(stub *shim.ChaincodeStub, function string, args []string)([]byte, error) {
    /*
    var durians [3]Durian;
    durians[0] = Durian{No : "1234567890", Status : 01}
    durians[1] = Durian{No : "2234567890", Status : 02}
    durians[2] = Durian{No : "3234567890", Status : 03}

    for i:=0; i<3; i++ {
        var bytes []byte
        bytes, _ = json.Marshal(durians[i])

        stub.PutState(strconv.Itoa(i), bytes)
    }
    */

	return nil, nil
}

func (cc *DurianChaincode) Invoke(stub *shim.ChaincodeStub, function string, args []string) ([]byte, error) {
	if function == "addDurian" {
		return cc.addDurian(stub, args)
	}
	return nil, errors.New("Received unknown function")
}

func (cc *DurianChaincode) addDurian(stub *shim.ChaincodeStub, args []string) ([]byte, error) {

    if len(args) != 2 {
        return nil, errors.New("Incorrect number of arguments. Expecting 2")
    }

	durian := Durian{}
	durian.No = args[0]
	durian.Status = args[1]

    durianNumberLength := 10
    durianStatusLength := 2
    durianNumberLimit := 10000000000
    durianStatusLimit := 100
    durianNumber, err := strconv.Atoi(durian.No)
    if err == nil && durianNumber < durianNumberLimit && len(durian.No) == durianNumberLength {
        durianStatus, err := strconv.Atoi(durian.Status)
        if err == nil && durianStatus < durianStatusLimit && len(durian.Status) == durianStatusLength {
            durianJson, _ := json.Marshal(durian)
            stub.PutState(durian.No, durianJson)
            return nil, nil
        } else {
            return nil, errors.New("durian status is abnormal " + durian.Status)
        }
    } else {
        return nil, errors.New("durian number is abnormal " + durian.No)
    }
}

//　카운터 정보 참조
func (cc *DurianChaincode) Query(stub *shim.ChaincodeStub, function string, args []string) ([]byte, error) {
	if function == "getDurians" {
		// 카운터 정보 취득
		return cc.getDurians(stub, args)
	}
	return nil, errors.New("Received unknown function")
}

// 카운터 정보 취득
func (cc *DurianChaincode) getDurians(stub *shim.ChaincodeStub, args []string) ([]byte, error) {

    keysIter, err := stub.RangeQueryState("", "")
    if err != nil {
        return nil, fmt.Errorf("keys operation failed. Error accessing state: %s", err)
    }
    defer keysIter.Close()

    var durians []Durian
    for keysIter.HasNext() {
        key, _, iterErr := keysIter.Next()
        if iterErr != nil {
            return nil, fmt.Errorf("keys operation failed. Error accessing state: %s", err)
        }
        bytes, _ := stub.GetState(key)
        durian := Durian{}
        json.Unmarshal(bytes, &durian)
        durians = append(durians, durian)
    }

	//json 형식으로 변환
	return json.Marshal(durians)
}

// Validating Peer에 연결해 체인 코드를 실행
func main() {
	err := shim.Start(new(DurianChaincode))
	if err != nil {
		fmt.Printf("Error starting chaincode: %s", err)
	}
}