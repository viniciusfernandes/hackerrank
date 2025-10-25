/**
 * @param {string[]} strs
 * @return {string}
 */
 const regex = /^[a-z]+$/
 const max = 200
 function isInvalid(word){
    return word ==null || 
            word ==undefined || 
            word.trim().length==0||
            word.length>200 || 
            !regex.test(word)
 }
var longestCommonPrefix = function(strs) {
    if(strs ==null || 
    strs ==undefined ||
     strs.length <= 0 ||
     strs.length > max ){
        return ''
    }
    let prefx = max
    for(let i =0; i< strs.length-1;i++){
        for(let k =i+1; k< strs.length;k++){
            if(isInvalid(strs[i]) || isInvalid(strs[k])){
                return ''
            }
            let l =  strs[i].length > strs[k].length ? strs[k].length :strs[i].length 
            let temp=-1
            for(let m =0; m< l;m++){
                if(strs[i].charAt(m)  != strs[k].charAt(m)){
                    break
                }
                temp =m
            }
            if(temp <0){
                return ''
            }
            if(temp < prefx){
                 prefx=temp
            }
        }
    }
    return strs[0].substring(0, prefx+1)
};

console.log(longestCommonPrefix(["dog","racecar","car"]))