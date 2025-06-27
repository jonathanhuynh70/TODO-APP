import "./App.css"
import ToDoList from './components/ToDoList';
import Header from './components/Header';
import { BrowserRouter, Routes, Route } from "react-router-dom";


function App() {
  return (
    <>
      <BrowserRouter>
        <Header />
        <Routes>
            <Route path="/" element={<ToDoList />}></Route>
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
