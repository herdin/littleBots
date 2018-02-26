/*BLOCK CHAIN*/
package blockchain.bitcoin;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

class BlockHeader {
    public Long version;
    public byte[] previousBlockHash;
    public byte[] merkletRootHash;
    public Long timestamp;
    public Long difficultyTarget;
    public Long nonce;
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb
        //version : numeric -> byte array Big-endian -> byte array Little-endian -> hex String
        .append(CoinUtils.byteArrayToHexString(CoinUtils.getReversedByteArray(CoinUtils.hexStringToByteArray(CoinUtils.numericToHexString(this.version, 4)))))
        //previousBlockHash : byte array Big-endian -> byte array Little-endian -> hex String 
        .append(CoinUtils.byteArrayToHexString(CoinUtils.getReversedByteArray(this.previousBlockHash)))
        .append(CoinUtils.byteArrayToHexString(CoinUtils.getReversedByteArray(this.merkletRootHash)))
        .append(CoinUtils.byteArrayToHexString(CoinUtils.getReversedByteArray(CoinUtils.hexStringToByteArray(CoinUtils.numericToHexString(this.timestamp, 4)))))
        .append(CoinUtils.byteArrayToHexString(CoinUtils.getReversedByteArray(CoinUtils.hexStringToByteArray(CoinUtils.numericToHexString(this.difficultyTarget, 4)))))
        .append(CoinUtils.byteArrayToHexString(CoinUtils.getReversedByteArray(CoinUtils.hexStringToByteArray(CoinUtils.numericToHexString(this.nonce, 4)))))
        ;
        return sb.toString();
    }//END OF FUNCTION
}//END OF CLASS

public class Block {
    private int blockSize;
    private BlockHeader blockHeader;
    private int transactionCount;
    private Object[] transaction;
    
    public Block(BlockHeader blockHeader, String[] transactions) {
        this.blockHeader = blockHeader;
        this.transaction = transaction;
    }//END OF FUNCTION
    
    public byte[] getBlockHash() throws NoSuchAlgorithmException {
        return CoinUtils.encSHA256(CoinUtils.encSHA256(CoinUtils.hexStringToByteArray(this.blockHeader.toString())));
    }//END OF FUNCTION
    
    public static void main(String[] args) throws NoSuchAlgorithmException {
        
        
        boolean MERKLE_TREE         = true;
        boolean CALCULATE_THRESHOLD = false;
        boolean MINING_EXAMPLE      = false;
        boolean OLD_CASE            = false;
        
        if(MERKLE_TREE) {
            
//        import bitcoin
//        hashes=[
//            "78d1ab01ed2f6b4f82a5226d158e04ba058fe670621d8a91c736c643f7dadd97",
//            "1c345b5a67482c7123bb9c36ce6c4c78c4a144015635340e0de3deddb203943d",
//            "2dad9099ca64a7be68e46f0c215d6d4ce9ccae5671dc7f7b8d4496c52e897a4a",
//            "ccf8f183bcb846e22097bcd27b3ead106ebab22b7a62c67975ad1d4b265880ae",
//            "fb8b5c47e9693f9834c9704460c4f8358c12ecf8ea2f0322ee2cad4f2a027618",
//            "a52179843aaccbf8b03d9702639cc5d5ae4d1420ba5609cf287108e4b7c762fd",
//            "d6f90a8c8dab80c47ac7ec420ceb885b1c7d1613623811213c1c16158eceb69f",
//            "213c0678d8761b6b86021fa5110c8f0e80c632c5a6d7456c6c891c30a166f40a",
//            "df7287f5b421883419db1ffb6d5fb3473df80a29fa09bdeee3ebefae38eb938c",
//            "f0d594814525caec5485d48ab8ffbf5788765e699c70f9681dccc5a3efb179b4",
//            "b9188753617e8b4207f3dec37d8da370cd902bc8e23ed32334c362ecacde8993"
//        ]
            String[] hashes = {
                    "78d1ab01ed2f6b4f82a5226d158e04ba058fe670621d8a91c736c643f7dadd97",
                    "1c345b5a67482c7123bb9c36ce6c4c78c4a144015635340e0de3deddb203943d",
                    "2dad9099ca64a7be68e46f0c215d6d4ce9ccae5671dc7f7b8d4496c52e897a4a",
                    "ccf8f183bcb846e22097bcd27b3ead106ebab22b7a62c67975ad1d4b265880ae",
                    "fb8b5c47e9693f9834c9704460c4f8358c12ecf8ea2f0322ee2cad4f2a027618",
                    "a52179843aaccbf8b03d9702639cc5d5ae4d1420ba5609cf287108e4b7c762fd",
                    "d6f90a8c8dab80c47ac7ec420ceb885b1c7d1613623811213c1c16158eceb69f",
                    "213c0678d8761b6b86021fa5110c8f0e80c632c5a6d7456c6c891c30a166f40a",
                    "df7287f5b421883419db1ffb6d5fb3473df80a29fa09bdeee3ebefae38eb938c",
                    "f0d594814525caec5485d48ab8ffbf5788765e699c70f9681dccc5a3efb179b4",
                    "b9188753617e8b4207f3dec37d8da370cd902bc8e23ed32334c362ecacde8993"
                  };
            class MerkleTree {
                private String[] hashs;
                public MerkleTree(String[] hashs) { this.hashs = hashs; }
//              def merkle_path(index): 
//              header={}
//              header['merkle_root']='f68cd8ad6571d29c91dbfea769a6a834ed50f231650406b4e62eefac958c6317'
//              proof=mk_merkle_proof(header, hashes, index)
//              siblings=proof['siblings']
//              print 'merkle path : ', siblings
//              target_tx=hashes[index]
//              siblings = map( lambda x: x.decode('hex')[::-1], siblings)
//              resultHash = target_tx.decode('hex')[::-1]
//              proofLen = len(siblings)
//              i = 0
//              while i < proofLen:
//                  proofHex = siblings[i]
//                  sideOfSibling = index % 2  # 0 means sibling is on the right; 1 means left
//                  if sideOfSibling == 1:
//                      left = proofHex
//                      right = resultHash
//                  elif sideOfSibling == 0:
//                      left = resultHash
//                      right = proofHex
//                  resultHash = bitcoin.bin_sha256(bitcoin.bin_sha256(left + right  ))
//                  index /= 2
//                  i += 1
//              assert resultHash[::-1].encode('hex') == header['merkle_root']
                public boolean merkle_path(int index) {
                    BlockHeader header = new BlockHeader();
                    header.merkletRootHash = CoinUtils.hexStringToByteArray("f68cd8ad6571d29c91dbfea769a6a834ed50f231650406b4e62eefac958c6317");
                    
                    
                    return false;
                }//END OF FUNCTION
                
//                slice는 start:stop[:step]의 형식으로 쓸 수있습니다. 여기서 [:step]은 써도 되고 안써도 된다는 의미입니다.
//
//                step을 명시하지 않을 경우에는
//
//                a[start:end] # start부터 end-1까지의 item
//                a[start:] # start부터 리스트 끝까지 item
//                a[:end] # 처음부터 end-1까지의 item
//                a[:] # 리스트의 모든 item
//                step value를 쓰는 경우에는
//
//                a[start:end:step]# start부터 end-1까지 step만큼 인덱스 증가시키면서
//                step을 지정할 때 :end에 유의하세요 end는 end부터 포함시키지 않겠다는 의미이지 end가 꼭 포함된다는 의미는 아닙니다.
//
//                또 start나 end가 음수가 음수인 경우에는 리스트의 끝에서부터 카운트하겠다는 의미입니다.
//
//                a[-1] # 맨 뒤의 item
//                a[-2:] # 맨 뒤에서부터 item2개
//                a[:-n] # 맨 뒤의 item n개 빼고 전부
                
//              def mk_merkle_proof(header, hashes, index):
//              nodes = [h.decode('hex')[::-1] for h in hashes] //헥사스트링을 헥사로 바꾸고 엔디안변환후 nodes 에 저장
//              if len(nodes) % 2 and len(nodes) > 2: //nodes 길이가 홀수면서 길이가 2보다 크면
//                  nodes.append(nodes[-1]) //끝에 마지막꺼를 한번더붙인다
//              layers = [nodes]
//              while len(nodes) > 1:
//                  newnodes = []
//                  for i in range(0, len(nodes) - 1, 2):
//                      newnodes.append(bitcoin.bin_sha256(bitcoin.bin_sha256(nodes[i] + nodes[i+1])))
//                  if len(newnodes) % 2 and len(newnodes) > 2:
//                      newnodes.append(newnodes[-1])
//                  nodes = newnodes
//                  layers.append(nodes)
//              # Sanity check, make sure merkle root is valid
//              assert nodes[0][::-1].encode('hex') == header['merkle_root']
//              merkle_siblings = \
//                  [layers[i][(index >> i) ^ 1] for i in range(len(layers)-1)]
//              return {
//                  "hash": hashes[index],
//                  "siblings": [x[::-1].encode('hex') for x in merkle_siblings],
//                  "header": header
//              }
                public void merkle_proof(BlockHeader header, String[] hashes, int index) {
//                    byte[] reversedHashes = new String
                }//END OF FUNCTION
            }//END OF CLASS
//         
//        if __name__ == '__main__':
//            merkle_path(5)
            new MerkleTree(hashes).merkle_path(5);
            
//            5번째 인덱스의 merkle path :
//                ['fb8b5c47e9693f9834c9704460c4f8358c12ecf8ea2f0322ee2cad4f2a027618', 
//                '8278e3e47bb6f0e7c0cadfc3ab6f395689251df1e31922f5e7c9851eae356290', 
//                'aafcb72ae5c43b5ab2e1fd20e91381aee67cc3363f87bc8e4ae2504dc9272316', 
//                '5e22d508e7ff865131de483a95afc02c75cf0cbeb763b705e4c7d68afa9d7386']
        }
        
        if(CALCULATE_THRESHOLD) {
            
            /*
            target threshold is a 256-bits unsigned integer which a header hash must be equal to or below in order for that header to be a valid part of the block chain.
            0x 1b0404cb
            0x 1b 0404cb
            0404cb      * 256  ^ (1b       - 3)
            significant * base ^ (exponent - bytets in significant)
            0x 1bc330000000000000000000000000000000000000000000
            byte length : 0x18 : 24
            */
            
            Long nBits = 0x181bc330L;
            Long significant = (nBits&0x00ffffff);
            Long exponent = (nBits>>>24);
            Long base = 256L;
            System.out.println("significant : " + CoinUtils.numericToHexString(significant, 4) + " : " + significant);
            System.out.println("exponent    : " + CoinUtils.numericToHexString(exponent, 4) + " : " + exponent);
            
            
            BigInteger biSignificant = BigInteger.valueOf(significant);
            BigInteger biExponent = BigInteger.valueOf(exponent);
            BigInteger biBase = BigInteger.valueOf(base);
            System.out.println("Integer.MAX_VALUE : " + Integer.MAX_VALUE);
            System.out.println("Long.MAX_VALUE : " + Long.MAX_VALUE);
            BigInteger biThreshold = biSignificant.multiply(biBase.pow(biExponent.subtract(BigInteger.valueOf(3L)).intValue()));
            System.out.println("biThreshold : " + biThreshold);
            System.out.println("biThreshold : " + CoinUtils.numericToHexString(biThreshold, 24));
        }
        
        if(MINING_EXAMPLE) {
            /**
             * Attack at 9PM!
             * 위의 텍스트에 숫자를 붙인뒤 SHA-256 해쉬를 돌렸을때, 앞에 3바이트가 0이 나오는 텍스트찾기
             */
            String msg = "Attack at 9PM!";
            String testMsg = "";
            System.out.println(CoinUtils.byteArrayToHexString(CoinUtils.encSHA256(msg.getBytes()))); //c53ae0b1db6f94ce4177112d386c8bdf2f1b1d949f5a19f47d473a785dc97afc
            int i = 0;
            while(true) {
                testMsg = msg+String.valueOf(i);
                byte[] tmpBytes = CoinUtils.encSHA256(testMsg.getBytes());
                System.out.println("index " + i + " : " + CoinUtils.byteArrayToHexString(tmpBytes));
                if(tmpBytes[0] == 0x00 && tmpBytes[1]>>>4 == 0x00) {
                    break;
                }
                i++;
            }
            if(testMsg.length() > 0)
                System.out.println("FIND SUCCESS : " + testMsg);
            else 
                System.out.println("FIND FAIL : " + testMsg);
        }
        
        if(OLD_CASE) {
            //140402 index bitcoin block hashing check
            String[] transactions = {"this is first transaction"};
            BlockHeader blockHeader1 = new BlockHeader();
            blockHeader1.version = 1L;
            blockHeader1.previousBlockHash = CoinUtils.hexStringToByteArray("00000000000008a3a41b85b8b29ad444def299fee21793cd8b9e567eab02cd81");
            blockHeader1.merkletRootHash = CoinUtils.hexStringToByteArray("2b12fcf1b09288fcaff797d71e950e71ae42b91e8bdb2304758dfcffc2b620e3");
            blockHeader1.timestamp = 1305998791L;
            blockHeader1.difficultyTarget = 440711666L;
            blockHeader1.nonce = 2504433986L;
            Block block1 = new Block(blockHeader1, transactions);
            System.out.println(CoinUtils.byteArrayToHexString((CoinUtils.getReversedByteArray(block1.getBlockHash()))).equals("00000000000000001e8d6829a8a21adc5d38d0a473b144b6765798e61f98bd1d"));
            
            //1661597 index bitcoin block hashing check
            String[] transactions2 = {"this is second transaction"};
            BlockHeader blockHeader2 = new BlockHeader();
            blockHeader2.version = 536870912L;
            blockHeader2.previousBlockHash = CoinUtils.hexStringToByteArray("0000000000000000006a5efe4a18b81bbabdd19f3633a5d4f06159fd6c7b69b2");
            blockHeader2.merkletRootHash = CoinUtils.hexStringToByteArray("a7469fd328050e44a2d7b19a5cff740a26b245acc1a6336ed4edfa0274ad0c8a");
            blockHeader2.timestamp = 1514521239L;
            blockHeader2.difficultyTarget = 402691653L;
            blockHeader2.nonce = 380969149L;
            Block block2 = new Block(blockHeader2, transactions2);
            System.out.println(CoinUtils.byteArrayToHexString((CoinUtils.getReversedByteArray(block2.getBlockHash()))).equals("00000000000000000079ecf3ba985ba5f769441511ca612ee9e289c424b790c1"));
        }
        
    }//END OF FUNCTION
}//END OF CLASS
/*
{ "blocks" : [
    {   "hash":"00000000000000001e8d6829a8a21adc5d38d0a473b144b6765798e61f98bd1d",
        "ver":1,
        "prev_block":"00000000000008a3a41b85b8b29ad444def299fee21793cd8b9e567eab02cd81",
        "mrkl_root":"2b12fcf1b09288fcaff797d71e950e71ae42b91e8bdb2304758dfcffc2b620e3",
        "time":1305998791,
        "bits":440711666,
        "fee":1000000,
        "nonce":2504433986,
        "n_tx":4,
        "size":1496,
        "block_index":140402,
        "main_chain":true,
        "height":125552,
        "tx":[]
    }
]}
{ "blocks" : [
    {   "hash":"0000000000001c0533ea776756cb6fdedbd952d3ab8bc71de3cd3f8a44cbaf85",
        "ver":1,
        "prev_block":"00000000000000001e8d6829a8a21adc5d38d0a473b144b6765798e61f98bd1d",
        "mrkl_root":"53fb6ea244d5f501a22c95c4c56701d70a6e115c5476ed95280cb22149c171b3",
        "time":1305999028,
        "bits":440711666,
        "fee":1000000,
        "nonce":2165053959,
        "n_tx":7,
        "size":2482,
        "block_index":140403,
        "main_chain":true,
        "height":125553,
        "tx":[]
    }
]}


{ "blocks" : [

{
    "hash":"00000000000000000079ecf3ba985ba5f769441511ca612ee9e289c424b790c1",
    "ver":536870912,
    "prev_block":"0000000000000000006a5efe4a18b81bbabdd19f3633a5d4f06159fd6c7b69b2",
    "mrkl_root":"a7469fd328050e44a2d7b19a5cff740a26b245acc1a6336ed4edfa0274ad0c8a",
    "time":1514521239,
    "bits":402691653,
    "fee":119509238,
    "nonce":380969149,
    "n_tx":281,
    "size":1004801,
    "block_index":1661597,
    "main_chain":true,
    "height":501515,
    "tx":[



*/