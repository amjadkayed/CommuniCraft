import React from 'react';
import RegistrationForm from './components/RegistrationForm';
import './App.css';

const App: React.FC = () => {
  return (
    <div className="App">
      <h1>User Registration</h1>
      <RegistrationForm />
    </div>
  );
};

export default App;
