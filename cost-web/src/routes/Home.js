import React from 'react';
import axios from 'axios';
import Item from '../components/Item';

import 'bootstrap/dist/css/bootstrap.min.css';


class Home extends React.Component{

    // constructor(props){
    //     super(props);

    //     this.inputRef = React.createRef();
    // }

    state = {
        isLoading: true,
        items: [],
    };

    getRecommandData = async() => {

        var distance = document.getElementById('distance').value;
        console.log(distance);

        const {
            data : items,
        } = await axios.get(process.env.REACT_APP_API_SERVER + '/recommend');

        this.setState({items, isLoading: false});
    }

    componentDidMount(){
        console.log(process.env.REACT_APP_API_SERVER);

        this.getRecommandData();
    };

    render(){
        const {isLoading, items} = this.state;

        console.log(this.state);

        return (
            <section className="container">
                <h1>Recommend Cost</h1>

                <div>
                    <select id="distance" className="form-select">
                        <option value="10">10</option>
                        <option value="20">20</option>
                        <option selected value="30">30</option>
                    </select>
                </div>

                <br/>
                {isLoading ? (
                    <div>
                        <span className="loader__text">
                            'Loading...'
                        </span>
                    </div>
                ) : (
                    <div className="items">
                        <table className="table">
                            <thead>
                                <tr>
                                    <th>start date</th>
                                    <th>end date</th>
                                    <th>periods</th>
                                    <th>bizDays</th>
                                    <th>dirrerence Cost</th>
                                    <th>card Cost</th>
                                    <th>periodical Cost</th>
                                    <th>holidays</th>
                                </tr>
                            </thead>
                            <tbody>
                                {items.map(item => (
                                    <Item
                                    startDate={item.startDate}
                                    endDate={item.endDate}
                                    periods={item.periods}
                                    bizDays={item.bizDays}
                                    differenceCost={item.differenceCost}
                                    cardCost={item.cardCost}
                                    periodicalCost={item.periodicalCost}
                                    holidays={item.holidays}
                                    />
                                ))}
                            </tbody>
                        </table>
                    </div>
                )}
            </section>
        );
    }
}

export default Home;