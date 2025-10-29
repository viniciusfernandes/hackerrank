/**
 * @param {number} x
 * @return {boolean}
 */
const MAX = Math.pow(2,31) -1
var isPalindrome = function(x) {
    if(!x || x<0 || x> MAX){
      
        return false
    }
    let digits =[]
    let r=0
    while (x>0){
        r = x%10
        x -= r
        x/= 10
        digits.push(r)
    }
    for(let i =0;i<digits.length/2 ;i++){
        if(digits[i]!==digits[digits.length -1 -i]){
            return false
        }
    }
    return true
};
console.log(isPalindrome(10))