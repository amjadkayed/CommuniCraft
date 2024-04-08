import React from 'react';

interface Props {
  message: string;
  error: boolean;
}

const AlertMessage: React.FC<Props> = ({ message, error }) => {
  return <div style={{ color: error ? 'red' : 'green' }}>{message}</div>;
};

export default AlertMessage;
