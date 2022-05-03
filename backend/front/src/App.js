import './App.css';
import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import {createBrowserHistory} from "history";
import NavigationBar from "./components/NavigationBar";
import Home from "./components/Home";
import Login from './components/Login';
const API_URL = 'http://localhost:8081/api/v1'
const AUTH_URL = 'http://localhost:8081/auth'

function App() {
 return (
 <div className="App">
 <BrowserRouter>
 <NavigationBar />
 <div className="container-fluid">
 <Routes>
 <Route path="home" element={<Home />}/>
 <Route path="Login" element={<Login />} />

 </Routes>
 </div>
 </BrowserRouter>
 </div>
 );
}




export default App;

