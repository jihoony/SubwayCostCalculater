import React from 'react';
import './App.css';

import {HashRouter, Route} from 'react-router-dom';

import Home from "./routes/Home";


function App() {
  return (
    <HashRouter>
      <Route path="/" exact={true} component={Home}/>
    </HashRouter>
  );
}

export default App;
