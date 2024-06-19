import React from 'react';
import {render, fireEvent} from '@testing-library/react';
import RegisterPagePropertyMgmt from './RegisterPagePropertyMgmt';

describe('RegisterPagePropertyMgmt', () => {
    it('renders without crashing', () => {
        render(<RegisterPagePropertyMgmt/>);
    });

    it('updates state on input change', () => {
        const {getByLabelText} = render(<RegisterPagePropertyMgmt/>);

        fireEvent.change(getByLabelText(/Agent Name:/i), {target: {value: 'Test Agent'}});
        fireEvent.change(getByLabelText(/Email:/i), {target: {value: 'test@example.com'}});
        fireEvent.change(getByLabelText(/Password:/i), {target: {value: 'password123'}});

        expect(getByLabelText(/Agent Name:/i).value).toBe('Test Agent');
        expect(getByLabelText(/Email:/i).value).toBe('test@example.com');
        expect(getByLabelText(/Password:/i).value).toBe('password123');
    });

    it('submits the form', () => {
        const {getByLabelText, getByRole} = render(<RegisterPagePropertyMgmt/>);
        const consoleSpy = jest.spyOn(console, 'log');

        fireEvent.change(getByLabelText(/Agent Name:/i), {target: {value: 'Test Agent'}});
        fireEvent.change(getByLabelText(/Email:/i), {target: {value: 'test@example.com'}});
        fireEvent.change(getByLabelText(/Password:/i), {target: {value: 'password123'}});

        fireEvent.click(getByRole('button', {name: /Register/i}));

        expect(consoleSpy).toHaveBeenCalledWith({
            agentName: 'Test Agent',
            email: 'test@example.com',
            password: 'password123'
        });
    });

    it('has empty initial state', () => {
        const { getByLabelText } = render(<RegisterPagePropertyMgmt />);

        expect(getByLabelText(/Agent Name:/i).value).toBe('');
        expect(getByLabelText(/Email:/i).value).toBe('');
        expect(getByLabelText(/Password:/i).value).toBe('');
    });
});