import { Accordion, AccordionSummary, Typography, AccordionDetails } from '@mui/material';
import RackGrid from './RackGrid';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

interface AisleProps {
    aisleData: any;
}

export const Aisle = ({ aisleData }: AisleProps) => {
    return (
        <div>
            <Accordion sx={{ marginBottom: 1 }} defaultExpanded key={aisleData.aisleName}>
                <AccordionSummary
                    expandIcon={<ExpandMoreIcon />}
                    aria-controls={`panel-${aisleData.aisleName}-content`}
                    id={`panel-${aisleData.aisleName}-header`}
                    sx={{ maxHeight: '20px' }}
                >
                    <Typography>Aisle: {aisleData.aisleName}</Typography>
                </AccordionSummary>
                <AccordionDetails sx={{ marginBottom: 1 }}>
                    <RackGrid aisle={aisleData} />
                </AccordionDetails>
            </Accordion>
            {/* {aisleData.aisleName}
            <RackGrid /> */}
        </div>
    );
};
