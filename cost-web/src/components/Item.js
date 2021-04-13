import React from 'react';

import PropTypes from 'prop-types';

function addCommas(nStr){
    nStr += '';
    var x = nStr.split('.');
    var x1 = x[0];
    var x2 = x.length > 1 ? '.' + x[1] : '';
    var rgx = /(\d+)(\d{3})/;
    while (rgx.test(x1)) {
        x1 = x1.replace(rgx, '$1' + ',' + '$2');
    }
    return x1 + x2;
}

function Items ({startDate, endDate, periods, bizDays, differenceCost, cardCost , periodicalCost, holidays}){
    return (
            <tr>
                <td>{startDate}</td>
                <td>{endDate}</td>
                <td>{bizDays}</td>
                <td>{periods}</td>
                <td className="text-right">{addCommas(differenceCost)}</td>
                <td className="text-right">{addCommas(cardCost)}</td>
                <td className="text-right">{addCommas(periodicalCost)}</td>
                <td>{holidays.join(', ')}</td>
            </tr>
    );
}

Items.prototype ={
    startDate: PropTypes.string.isRequired,
    endDate: PropTypes.string.isRequired,
    periods: PropTypes.number.isRequired,
    bizDays: PropTypes.number.isRequired,
    differenceCost: PropTypes.number.isRequired,
    cardCost: PropTypes.number.isRequired,
    periodicalCost: PropTypes.number.isRequired,
    holidays: PropTypes.arrayOf(PropTypes.string)
};

export default Items;