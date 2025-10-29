/**
 * @param {number} num
 * @return {string}
 */
const romanValues = [1000, 500, 100, 50, 10, 5, 1]
const romanSymbols = new Map(
    [
        [1, 'I'],
        [5, 'V'],
        [10, 'X'],
        [50, 'L'],
        [100, 'C'],
        [500, 'D'],
        [1000, 'M']
    ]
)

function getRomanPair(num) {
    for (let k = 0; k < romanValues.length; k++) {
        if (romanValues[k] <= num) {
            return [romanValues[k], romanSymbols.get(romanValues[k])]
        }
    }
    throw Error('Symbol do not exist for number=' + num)
}

function getRomanSymbol(num) {
    let romanPair = getRomanPair(num)
    let romanSymbol = romanPair[1]

    let rest = num - romanPair[0]
    let multiple = rest
    let digit = 0
    while (multiple > 0) {
        digit = multiple % 10
        multiple -= digit
        multiple /= 10
    }
    if (digit) {
        let restSymbol = getRomanPair(rest)[1]
        for (let k = 0; k < digit; k++) {
            romanSymbol += restSymbol
        }
    }
    return romanSymbol
}

var intToRoman = function (num) {
    if (!num || num < 1 || num > 3999) {
        return ''
    }

    let digits = []
    let r = 0
    while (num > 0) {
        r = num % 10
        num -= r
        num /= 10
        digits.push(r)
    }

    let asRoman = ''
    let c = 0
    let value = 0
    for (let j = digits.length - 1; j >= 0; j--) {
        if (digits[j] == 0) {
            continue
        }
        c = j
        value = digits[j]
        while (c > 0) {
            value *= 10
            c--
        }

        if (digits[j] == 4 || digits[j] == 9) {
            if (value == 4) {
                asRoman += 'IV'
            } else if (value == 40) {
                asRoman += 'XL'
            } else if (value == 400) {
                asRoman += 'CD'
            } else if (value == 9) {
                asRoman += 'IX'
            } else if (value == 90) {
                asRoman += 'XC'
            } else if (value == 900) {
                asRoman += 'CM'
            }
        } else {
            asRoman += getRomanSymbol(value)
        }

    }
    return asRoman
};

console.log(intToRoman(1994))