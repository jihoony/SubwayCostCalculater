import React from 'react';
import axios from 'axios';
import Item from '../components/Item';

import 'bootstrap/dist/css/bootstrap.min.css';


class Home extends React.Component{

    state = {
        isLoading: true,
        items: [],
    };

    getRecommendData = async() => {

        const {
            data : items,
        } = await axios.get(process.env.REACT_APP_API_SERVER + '/recommend');

        this.setState({items, isLoading: false});
    }

    componentDidMount(){

        this.getRecommendData();
    };

    render(){
        const {isLoading, items} = this.state;

        return (
            <section className="container">
                <h1>Recommend Cost</h1>

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
                                    <th>difference Cost</th>
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