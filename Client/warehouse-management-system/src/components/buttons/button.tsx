import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    IconButton,
    Slide,
    Tooltip,
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import React from 'react';
import { TransitionProps } from '@mui/material/transitions';
import AnnouncementIcon from '@mui/icons-material/Announcement';
import { ConfirmDialog } from '../dialog';

interface DeleteButtonProps {
    onDelete: () => void;
    disabled?: false | boolean;
}

const Transition = React.forwardRef(function Transition(
    props: TransitionProps & {
        children: React.ReactElement<any, any>;
    },
    ref: React.Ref<unknown>,
) {
    return <Slide direction="up" ref={ref} {...props} />;
});

const titleDeleteConfirm = () => {
    return (
        <div
            style={{
                display: 'flex',
                alignItems: 'center',
                fontSize: '50px',
                justifyContent: 'center',
                gap: '10px',
            }}
        >
            <AnnouncementIcon fontSize="inherit" color="error" />
            <h2 style={{ fontSize: '25px' }}>Confirm Delete</h2>
        </div>
    );
};

const contentDeleteConfirm = () => {
    return 'Are you sure you want to delete?';
};
export const DeleteButtonIcon = (props: DeleteButtonProps) => {
    const [open, setOpen] = React.useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleNegative = () => {
        props.onDelete();
        setOpen(false);
    };
    return (
        <React.Fragment>
            <Tooltip title="Delete">
                <IconButton disabled={props.disabled} onClick={handleClickOpen} aria-label="fingerprint" color="error">
                    <DeleteIcon />
                </IconButton>
            </Tooltip>
            <ConfirmDialog
                open={open}
                handleClose={handleClose}
                negativeText="Yes, Delete"
                positiveText="No, Cancel"
                positiveHandle={handleClose}
                negativeHandle={handleNegative}
                transition={Transition}
                content={contentDeleteConfirm()}
                title={titleDeleteConfirm()}
            />
        </React.Fragment>
    );
};

export const DeleteButton = (props: DeleteButtonProps) => {
    const [open, setOpen] = React.useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };
    return (
        <React.Fragment>
            <Tooltip title="Delete">
                <Button
                    style={{
                        fontWeight: 700,
                        textTransform: 'none',
                    }}
                    startIcon={<DeleteIcon />}
                    variant="contained"
                    onClick={handleClickOpen}
                    aria-label="delete"
                    color="error"
                >
                    Delete
                </Button>
            </Tooltip>
            <ConfirmDialog
                open={open}
                handleClose={handleClose}
                negativeText="Yes, Delete"
                positiveText="No, Cancel"
                positiveHandle={handleClose}
                negativeHandle={props.onDelete}
                transition={Transition}
                content={contentDeleteConfirm()}
                title={titleDeleteConfirm()}
            />
        </React.Fragment>
    );
};
